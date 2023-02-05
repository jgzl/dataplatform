package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayBalancedDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayBalancedDO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Description 负载管理业务实现类
 * @Author jianglong
 * @Date 2020/06/28
 * @Version V1.0
 */
@Service
public class GatewayBalancedService extends BaseService<GatewayBalancedDO, String, GatewayBalancedDao> {

    @Resource
    private GatewayLoadServerService gatewayLoadServerService;

    /**
     * 删除负载以及注册到负载的路由服务
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Throwable.class})
    public void deleteAndServer(String id){
        gatewayLoadServerService.deleteAllByBalancedId(id);
        this.deleteById(id);
    }
}
