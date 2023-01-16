package cn.cleanarch.dp.common.gray.feign;

import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Feign拦截器
 * @author 李海峰
 * @date 2022年06月23日 17:06
 */
public class GrayFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = WebmvcUtil.getRequest();
        if (httpServletRequest != null) {
            Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
            if (headerNames!=null) {
                while (headerNames.hasMoreElements()) {
                    String key = headerNames.nextElement();
                    String values = httpServletRequest.getHeader(key);
//                    if (key.equals("content-length")) {
//                        continue;
//                    }
                    requestTemplate.header(key,values);
                }
            }
        }
    }
}