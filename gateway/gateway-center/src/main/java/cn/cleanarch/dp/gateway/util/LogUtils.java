package cn.cleanarch.dp.gateway.util;

import cn.cleanarch.dp.gateway.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.listener.GatewayRequestLogApplicationEvent;
import cn.hutool.extra.spring.SpringUtil;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 记录日志到ES中的公用方法
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
@Slf4j
public class LogUtils {

    /**
     * 记录xml/json格式请求返回日志数据-有body
     *
     * @param gatewayLog
     * @param buffer
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends DataBuffer> T loggingResponse(GatewayLogDO gatewayLog, T buffer) {
        GatewayRequestLogApplicationEvent event = new GatewayRequestLogApplicationEvent(gatewayLog, gatewayLog);
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
    public static <T extends DataBuffer> T loggingResponse(GatewayLogDO gatewayLog, List<T> dataBuffers) {
        GatewayRequestLogApplicationEvent event = new GatewayRequestLogApplicationEvent(gatewayLog, gatewayLog);
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
    public static void loggingResponse(GatewayLogDO gatewayLog) {
        GatewayRequestLogApplicationEvent event = new GatewayRequestLogApplicationEvent(gatewayLog, gatewayLog);
        SpringUtil.publishEvent(event);
    }
}