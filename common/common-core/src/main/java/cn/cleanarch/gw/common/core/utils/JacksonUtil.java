package cn.cleanarch.gw.common.core.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON 工具类
 *
 * @author lihaifeng
 */
@UtilityClass
@Slf4j
public class JacksonUtil {
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    static {
        log.info("开始初始化自动配置Jackson的ObjectMapper");
        //设置序列化的域(属性,方法etc)以及修饰范围,Any包括private,public 默认是public的
        //ALL所有方位,ANY所有修饰符
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //如果java.time包下Json报错,添加如下两行代码
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        //忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        log.info("结束初始化自动配置Jackson的ObjectMapper");
    }

    /**
     * 初始化 objectMapper 属性
     * <p>
     * 通过这样的方式创建的ObjectMapper给到spring
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    @SneakyThrows
    public String toJsonString(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    @SneakyThrows
    public byte[] toJsonByte(Object object) {
        return objectMapper.writeValueAsBytes(object);
    }


    public <T> T parseObject(String text, Class<T> clazz) {
        if (StrUtil.isEmpty(text)) {
            return null;
        }
        try {
            return objectMapper.readValue(text, clazz);
        } catch (IOException e) {
            log.error("json parse err,json:{}", text, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字符串解析成指定类型的对象
     * 使用 {@link #parseObject(String, Class)} 时，在@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS) 的场景下，
     * 如果 text 没有 class 属性，则会报错。此时，使用这个方法，可以解决。
     *
     * @param text 字符串
     * @param clazz 类型
     * @return 对象
     */
    public <T> T parseObject2(String text, Class<T> clazz) {
        if (StrUtil.isEmpty(text)) {
            return null;
        }
        return JSONUtil.toBean(text, clazz);
    }

    @SneakyThrows
    public <T> T parseObject(byte[] bytes, Class<T> clazz) {
        if (ArrayUtil.isEmpty(bytes)) {
            return null;
        }
        return objectMapper.readValue(bytes, clazz);
    }

    @SneakyThrows
    public <T> T parseObject(String text, TypeReference<T> typeReference) {
        return objectMapper.readValue(text, typeReference);
    }

    @SneakyThrows
    public <T> List<T> parseArray(String text, Class<T> clazz) {
        if (StrUtil.isEmpty(text)) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(text, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    @SneakyThrows
    public JsonNode readTree(String text) {
        return objectMapper.readTree(text);
    }

    @SneakyThrows
    public JsonNode readTree(byte[] text) {
        return objectMapper.readTree(text);
    }


    /**
     * bean、array、List、Map --> json
     *
     * @param obj
     * @return json string
     * @throws Exception
     */
    @SneakyThrows
    public String writeValueAsString(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    /**
     * string --> bean、Map、List(array)
     *
     * @param jsonStr
     * @param valueType
     * @return obj
     * @throws Exception
     */
    @SneakyThrows
    public <T> T readValue(String jsonStr, TypeReference<T> valueType) {
        return objectMapper.readValue(jsonStr, valueType);
    }

    /**
     * string --> List<Bean>...
     *
     * @param jsonStr
     * @param parametrized
     * @param parameterClasses
     * @param <T>
     * @return
     */
    @SneakyThrows
    public <T> T readValue(String jsonStr, Class<?> parametrized, Class<?>... parameterClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
        return objectMapper.readValue(jsonStr, javaType);
    }

    @SneakyThrows
    public <T> T readValue(InputStream src, Class<T> valueType) {
        return objectMapper.readValue(src,valueType);
    }

}
