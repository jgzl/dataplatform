package cn.cleanarch.dp.gateway.configuration.properties;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 路径参数
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/20
 */
@Data
public class GatewayPathProperties {
    /**
     * 网关白名单(访问，限流，降级)
     */
    private List<String> whitePath = Lists.newArrayList();
    /**
     * 网关黑名单(访问，限流，降级)
     */
    private List<String> blackPath = Lists.newArrayList("gateway-admin/**");
}
