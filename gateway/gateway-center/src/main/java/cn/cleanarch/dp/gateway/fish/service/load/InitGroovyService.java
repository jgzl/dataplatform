package cn.cleanarch.dp.gateway.fish.service.load;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GroovyScript;
import cn.cleanarch.dp.common.gateway.ext.service.GroovyScriptService;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.Md5Utils;
import cn.cleanarch.dp.gateway.fish.service.DynamicGroovyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 初始化加载groovyScript规则引擎动态脚本
 * @Author JL
 * @Date 2022/2/22
 * @Version V1.0
 */
@Slf4j
@Service
public class InitGroovyService {

    @Resource
    private GroovyScriptService groovyScriptService;
    @Resource
    private DynamicGroovyService dynamicGroovyService;

    /**
     *  初始化加载groovyScript规则引擎动态脚本，并缓存实例化对象
     */
    @PostConstruct
    public void initLoadGroovyScript(){
        //查询已启用的groovyScript规则引擎动态脚本
        GroovyScript groovyScript = new GroovyScript();
        groovyScript.setStatus(Constants.YES);
        List<GroovyScript> groovyScriptList = groovyScriptService.list(groovyScript);
        if (CollectionUtils.isEmpty(groovyScriptList)){
            log.info("未初始化groovyScript规则引擎动态脚本，脚本集合数量：0！");
            return ;
        }
        String md5;
        for (GroovyScript script : groovyScriptList){
            md5 = Md5Utils.md5Str(script.getId() + script.getContent());
            dynamicGroovyService.instance(script, md5, true);
        }
    }

}
