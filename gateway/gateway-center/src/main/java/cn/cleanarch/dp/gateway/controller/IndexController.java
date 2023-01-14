package cn.cleanarch.dp.gateway.controller;

import cn.cleanarch.dp.common.core.annotation.ApiVersion;
import cn.cleanarch.dp.common.core.constant.GatewayConstants;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.feign.GatewayAdminFeign;
import cn.hutool.core.thread.ThreadUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 主页
 *
 * @author li7hai26@gmail.com
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class IndexController {

    private final GatewayAdminFeign gatewayAdminFeign;
    /**
     * 根节点跳转
     *
     * @return
     */
    @ApiVersion("1.1.0")
    @GetMapping("/")
    public Mono<R<String>> index1() {
        return Mono.just(R.success("success,version1.1.0"));
    }

    /**
     * 根节点跳转
     *
     * @return
     */
    @ApiVersion("1.2")
    @GetMapping("/")
    public Mono<R<String>> index2() {
        return Mono.just(R.success("success,version1.2"));
    }

    /**
     * 根节点跳转
     *
     * @return
     */
    @ApiVersion("1.3")
    @GetMapping("/")
    public Mono<R<String>> index3() {
        return Mono.just(R.success("success,version1.3"));
    }

    @SneakyThrows
    @GetMapping(GatewayConstants.ADMIN_PREFIX+"/heartbeat")
    public Mono<R<String>> heartbeat() {
        log.info("发起心跳请求");
        R<String> result = ThreadUtil.execAsync(gatewayAdminFeign::heartbeat).get();
        return Mono.just(result);
    }

    @SneakyThrows
    @GetMapping(GatewayConstants.ADMIN_PREFIX+"/test/record/log")
    public Mono<R<List<GatewayLogDO>>> testRecordLogByFeign() {
        GatewayLogDO gatewayLog = new GatewayLogDO();
        gatewayLog.setApiKey("test-record-log-key");
        gatewayLog.setApiSecret("test-record-log-secret");
        R<List<GatewayLogDO>> result = ThreadUtil.execAsync(()->gatewayAdminFeign.saveAll(Lists.newArrayList(gatewayLog))).get();
        return Mono.just(result);
    }
}