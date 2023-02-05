package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewayApiDocDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewayApiDocDO;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * @Description 网关路由API接口文档业务实现类
 * @Author JL
 * @Date 2020/11/25
 * @Version V1.0
 */
@Service
public class GatewayApiDocService extends BaseService<GatewayApiDocDO, String, GatewayApiDocDao> {
    private EntityManager entityManager;
}
