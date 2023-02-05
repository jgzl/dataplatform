package cn.cleanarch.dp.gateway.fish.service.load;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayGroovyScriptDO;
import cn.cleanarch.dp.common.gateway.ext.service.GatewayGroovyScriptService;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.common.gateway.ext.util.Md5Utils;
import cn.cleanarch.dp.gateway.fish.service.DynamicGatewayGroovyService;
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
public class InitGatewayGroovyService {

    @Resource
    private GatewayGroovyScriptService gatewayGroovyScriptService;
    @Resource
    private DynamicGatewayGroovyService dynamicGatewayGroovyService;

    /**
     *  初始化加载groovyScript规则引擎动态脚本，并缓存实例化对象
     */
    @PostConstruct
    public void initLoadGroovyScript(){
        //查询已启用的groovyScript规则引擎动态脚本
        GatewayGroovyScriptDO gatewayGroovyScriptDO = new GatewayGroovyScriptDO();
        gatewayGroovyScriptDO.setStatus(Constants.YES);
        List<GatewayGroovyScriptDO> gatewayGroovyScriptDOList = gatewayGroovyScriptService.list(gatewayGroovyScriptDO);
        if (CollectionUtils.isEmpty(gatewayGroovyScriptDOList)){
            log.info("未初始化groovyScript规则引擎动态脚本，脚本集合数量：0！");
            return ;
        }
        String md5;
        for (GatewayGroovyScriptDO script : gatewayGroovyScriptDOList){
            md5 = Md5Utils.md5Str(script.getId() + script.getContent());
            dynamicGatewayGroovyService.instance(script, md5, true);
        }
    }

}
