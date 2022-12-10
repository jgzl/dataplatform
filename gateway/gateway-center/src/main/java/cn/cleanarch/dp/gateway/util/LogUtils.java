package cn.cleanarch.dp.gateway.util;

import cn.cleanarch.dp.gateway.domain.GatewayLogDO;
import cn.cleanarch.dp.gateway.listener.GatewayRequestLogApplicationEvent;
import cn.hutool.extra.spring.SpringUtil;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * 记录日志到ES中的公用方法
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
@Slf4j
public class LogUtils {

    public static final List<MediaType> legalLogMediaTypes = Arrays.asList(
            MediaType.TEXT_XML,
            MediaType.APPLICATION_XML,
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_XML,
            MediaType.MULTIPART_FORM_DATA);

    /**
     * 记录xml/json格式请求返回日志数据-有body
     *
     * @param gatewayLog
     * @param buffer
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends DataBuffer> T logging(GatewayLogDO gatewayLog, T buffer) {
        GatewayRequestLogApplicationEvent event = new GatewayRequestLogApplicationEvent(gatewayLog.getId(), gatewayLog);
//        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
        NettyDataBufferFactory dataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
        byte[] content = new byte[buffer.readableByteCount()];
        buffer.read(content);

        // 释放掉内存
        DataBufferUtils.release(buffer);
        String responseBody = new String(content, StandardCharsets.UTF_8);
        gatewayLog.setResponseBody(responseBody);
        SpringUtil.publishEvent(event);
        return (T) dataBufferFactory.wrap(content);
    }

    /**
     * 记录xml/json格式请求返回日志数据-有body
     *
     * @param gatewayLog
     * @param dataBuffers
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends DataBuffer> T logging(GatewayLogDO gatewayLog, List<T> dataBuffers) {
        GatewayRequestLogApplicationEvent event = new GatewayRequestLogApplicationEvent(gatewayLog.getId(), gatewayLog);
//        DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
        NettyDataBufferFactory dataBufferFactory = new NettyDataBufferFactory(new UnpooledByteBufAllocator(false));
        DataBuffer buffer = dataBufferFactory.join(dataBuffers);
        byte[] content = new byte[buffer.readableByteCount()];
        buffer.read(content);

        // 释放掉内存
        DataBufferUtils.release(buffer);
        String responseBody = new String(content, StandardCharsets.UTF_8);
        gatewayLog.setResponseBody(responseBody);
        SpringUtil.publishEvent(event);
        return (T) dataBufferFactory.wrap(content);
    }

    /**
     * 记录非xml/json格式请求返回日志数据-无body
     *
     * @param gatewayLog
     */
    public static void logging(GatewayLogDO gatewayLog) {
        GatewayRequestLogApplicationEvent event = new GatewayRequestLogApplicationEvent(gatewayLog.getId(), gatewayLog);
        SpringUtil.publishEvent(event);
    }
}