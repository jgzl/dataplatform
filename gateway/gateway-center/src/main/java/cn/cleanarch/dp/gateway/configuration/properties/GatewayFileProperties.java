package cn.cleanarch.dp.gateway.configuration.properties;

import lombok.Data;

/**
 * 文件参数
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/20
 */
@Data
public class GatewayFileProperties {
    /**
     * 请求提大小(默认单位MB)
     */
    private Integer fileMaxSize = 20;
}
