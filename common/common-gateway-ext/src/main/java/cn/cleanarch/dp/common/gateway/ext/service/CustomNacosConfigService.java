package cn.cleanarch.dp.common.gateway.ext.service;

import cn.cleanarch.dp.common.gateway.ext.util.GatewayRouteConstants;
import cn.cleanarch.dp.common.gateway.ext.vo.GatewayNacosConfigBean;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description 将自定义配置推送到nacos配置中心
 * @Author JL
 * @Date 2021/09/23
 * @Version V1.0
 */
@Slf4j
@Service
public class CustomNacosConfigService {
    @Resource
    private NacosConfigProperties configProperties;

    /**
     * 将网关负载均衡配置推送到nacos中
     * @param balancedId
     */
    public void publishBalancedNacosConfig(final String balancedId){
        GatewayNacosConfigBean gatewayNacosConfig = new GatewayNacosConfigBean();
        gatewayNacosConfig.setBalancedId(balancedId);
        publishConfigToNacos(gatewayNacosConfig);
    }

    /**
     * 将网关路由配置推送到nacos中
     * @param routeId
     */
    public void publishRouteNacosConfig(final String routeId){
        GatewayNacosConfigBean gatewayNacosConfig = new GatewayNacosConfigBean();
        gatewayNacosConfig.setRouteId(routeId);
        publishConfigToNacos(gatewayNacosConfig);
    }

    /**
     * 将注册到网关路由的客户端服务配置推送到nacos中
     * @param regServerId
     */
    public void publishRegServerNacosConfig(final Long regServerId){
        GatewayNacosConfigBean gatewayNacosConfig = new GatewayNacosConfigBean();
        gatewayNacosConfig.setRegServerId(regServerId);
        publishConfigToNacos(gatewayNacosConfig);
    }

    /**
     * 将网关客户端ID推送到nacos中
     * @param clientId
     */
    public void publishClientNacosConfig(final String clientId){
        GatewayNacosConfigBean gatewayNacosConfig = new GatewayNacosConfigBean();
        gatewayNacosConfig.setClientId(clientId);
        publishConfigToNacos(gatewayNacosConfig);
    }

    /**
     * 将IP鉴权配置推送到nacos中
     * @param ip
     */
    public void publishIpNacosConfig(final String ip){
        GatewayNacosConfigBean gatewayNacosConfig = new GatewayNacosConfigBean();
        gatewayNacosConfig.setIp(ip);
        publishConfigToNacos(gatewayNacosConfig);
    }

    /**
     * 将groovyScript规则引擎动态脚本ID配置推送到nacos中
     * @param groovyScriptId
     */
    public void publishGroovyScriptNacosConfig(final Long groovyScriptId){
        GatewayNacosConfigBean gatewayNacosConfig = new GatewayNacosConfigBean();
        gatewayNacosConfig.setGroovyScriptId(groovyScriptId);
        publishConfigToNacos(gatewayNacosConfig);
    }

    /**
     * 将网关配置推送到nacos中, dataId 的完整格式如下：
     * {prefix}-${spring.profiles.active}.${file-extension}
     * @param gatewayNacosConfig
     */
    public void publishConfigToNacos(GatewayNacosConfigBean gatewayNacosConfig) {
        String dataId = GatewayRouteConstants.NACOS_DATA_ID;
        publishConfig(dataId, configProperties.getGroup(), gatewayNacosConfig.getGatewayConfig());
    }

    /**
     * 推送配置到nacos指定dataId的group
     * @param dataId
     * @param group
     * @param content
     */
    public void publishConfig(String dataId, String group, String content){
        try {
            configProperties.configServiceInstance().publishConfig(dataId, group, content);
        } catch(NacosException e){
            log.error("推送配置到Nacos异常！" + e.getErrMsg(), e);
        }
    }

}
