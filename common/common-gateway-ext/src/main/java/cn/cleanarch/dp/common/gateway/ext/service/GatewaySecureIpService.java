package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.base.BaseService;
import cn.cleanarch.dp.common.gateway.ext.dao.GatewaySecureIpDao;
import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewaySecureIpDO;
import cn.cleanarch.dp.common.gateway.ext.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

/**
 * @Description IP管理业务操作类
 * @Author jianglong
 * @Date 2020/05/28
 * @Version V1.0
 */
@Service
public class GatewaySecureIpService extends BaseService<GatewaySecureIpDO, String, GatewaySecureIpDao> {

    /**
     * 分页查询（支持模糊查询）
     * @param gatewaySecureIpDO
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<GatewaySecureIpDO> pageList(GatewaySecureIpDO gatewaySecureIpDO, int currentPage, int pageSize){
        //构造条件查询方式
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotBlank(gatewaySecureIpDO.getIp())) {
            //支持模糊条件查询
            matcher = matcher.withMatcher("ip", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        return this.pageList(gatewaySecureIpDO, matcher, currentPage, pageSize);
    }

}
