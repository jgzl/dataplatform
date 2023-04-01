package cn.cleanarch.dp.gateway.service.load;

import cn.cleanarch.dp.common.gateway.ext.dataobject.GatewaySecureIpDO;
import cn.cleanarch.dp.common.gateway.ext.service.GatewaySecureIpService;
import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import cn.cleanarch.dp.gateway.cache.IpListCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Description  初始化网关路IP名单配置
 * @Author JL
 * @Date 2021/09/22
 * @Version V1.0
 */
@Slf4j
@Service
public class InitGatewaySecureIpService {

    @Resource
    private GatewaySecureIpService gatewaySecureIpService;

    /**
     * 第一次初始化加载
     */
    @PostConstruct
    public void initLoadSecureIp(){
        GatewaySecureIpDO gatewaySecureIpDO = new GatewaySecureIpDO();
//        secureIp.setStatus(Constants.YES);
        List<GatewaySecureIpDO> list = gatewaySecureIpService.findAll(gatewaySecureIpDO);
        IpListCache.clear();
        int size = 0;
        if (!CollectionUtils.isEmpty(list)){
            size = list.size();
            list.forEach(s -> IpListCache.put(s.getIp(), s.getStatus().equals(Constants.YES)));
        }
        log.info("初始化加载IP配置共{}条", size);
    }

}
