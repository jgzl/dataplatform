package com.github.gw.gateway.configuration.properties;

import com.github.gw.gateway.common.GatewayConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 路径参数
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/20
 */
@Data
@ConfigurationProperties(prefix = GatewayConstants.SYSTEM_PREFIX)
public class GatewayProperties {
    private Integer limitBodySize = GatewayConstants.DEFAULT_LIMIT;
    private GatewayPathProperties path = new GatewayPathProperties();
    private GatewayFileProperties file = new GatewayFileProperties();
}