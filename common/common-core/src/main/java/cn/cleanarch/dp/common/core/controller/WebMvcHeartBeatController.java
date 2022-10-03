package cn.cleanarch.dp.common.core.controller;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.model.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * webmvc通用心跳管理模块
 *
 * @author li7hai26@gmail.com
 */
@RestController
@RequestMapping(CommonConstants.HEART_BEAT_URL)
@ConditionalOnWebApplication(type = Type.SERVLET)
public class WebMvcHeartBeatController {
    /**
     * 心跳
     *
     * @return
     */
    @GetMapping
    public R<String> heartbeat() {
        return R.success("heartbeat success");
    }
}
