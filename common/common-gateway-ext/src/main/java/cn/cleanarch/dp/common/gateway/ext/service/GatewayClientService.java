package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayClientDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayClientDO;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayRegServerDO;
import cn.cleanarch.dp.common.gateway.ext.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description 客户端管理业务类
 * @Author jianglong
 * @Date 2020/05/16
 * @Version V1.0
 */
@Service
public class GatewayClientService extends BaseService<GatewayClientDO,String, GatewayClientDao> {

    @Resource
    private GatewayRegServerService gatewayRegServerService;

    /**
     * 删除客户端
     * @param gatewayClientDO
     */
    @Override
    public void delete(GatewayClientDO gatewayClientDO){
        GatewayRegServerDO gatewayRegServerDO = new GatewayRegServerDO();
        gatewayRegServerDO.setClientId(gatewayClientDO.getId());
        //查找是否有注册到其它网关服务上，如有一并删除
        List<GatewayRegServerDO> gatewayRegServerDOList = gatewayRegServerService.findAll(gatewayRegServerDO);
        if (!CollectionUtils.isEmpty(gatewayRegServerDOList)){
            gatewayRegServerService.delete(gatewayRegServerDO);
        }
        super.delete(gatewayClientDO);
    }

    /**
     * 分页查询（支持模糊查询）
     * @param gatewayClientDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<GatewayClientDO> pageList(GatewayClientDO gatewayClientDO, int currentPage, int pageSize){
        //构造条件查询方式
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotBlank(gatewayClientDO.getName())) {
            //支持模糊条件查询
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        return this.pageList(gatewayClientDO, matcher, currentPage, pageSize);
    }
}
