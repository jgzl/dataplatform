package cn.cleanarch.dp.common.core.controller;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * webflux通用心跳管理模块
 *
 * @author li7hai26@gmail.com
 */
@Slf4j
@RestController
@RequestMapping(CommonConstants.HEART_BEAT_URL)
@ConditionalOnWebApplication(type = Type.REACTIVE)
public class ReactiveHeartBeatController {
    /**
     * 心跳
     *
     * @return
     */
    @GetMapping
    public Mono<R<String>> heartbeat() {
        log.info("接收心跳请求");
        return Mono.just(R.success("heartbeat success"));
    }
}
