package cn.cleanarch.dp.common.gray.feign;

import cn.hutool.core.util.StrUtil;
import cn.cleanarch.dp.common.core.constant.GatewayConstants;
import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Feign拦截器
 * @author 李海峰
 * @date 2022年06月23日 17:06
 */
public class GrayFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String version = WebmvcUtil.getRequest() != null ? WebmvcUtil.getRequest().getHeader(GatewayConstants.X_BUSINESS_API_VERSION) : HeaderVersionHolder.getVersion();
        if (StrUtil.isBlank(version)){
            version= HeaderVersionHolder.getVersion();
        }
        requestTemplate.header(GatewayConstants.X_BUSINESS_API_VERSION,version);
    }
}