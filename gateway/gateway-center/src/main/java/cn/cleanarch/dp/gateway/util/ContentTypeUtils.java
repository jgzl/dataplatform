package cn.cleanarch.dp.gateway.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

/**
 * @author li7hai26@outlook.com
 */
public class ContentTypeUtils {

    public static final List<MediaType> legalLogMediaTypes = Arrays.asList(
            MediaType.TEXT_XML,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML,
            MediaType.MULTIPART_FORM_DATA);

    public static Boolean validText(MediaType mediaType) {
        if (mediaType!=null) {
            return validText(mediaType.getType());
        }
        return false;
    }

    public static Boolean validText(String mediaType) {
        if (StrUtil.isBlank(mediaType)) {
            return false;
        }
        for (MediaType type : legalLogMediaTypes) {
            if (mediaType.contains(type.getType())) {
                return true;
            }
        }
        return false;
    }
}
