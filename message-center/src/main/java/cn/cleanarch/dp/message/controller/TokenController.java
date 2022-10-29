package cn.cleanarch.dp.message.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.JacksonUtil;
import cn.cleanarch.dp.common.redis.RedisHelper;
import cn.cleanarch.dp.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.dp.common.websocket.vo.UserVO;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息中心-Token管理控制器
 * @author li7hai26@gmail.com
 */
@RestController
@RequestMapping("/token/")
@RequiredArgsConstructor
public class TokenController {

    private final RedisHelper redisHelper;

    /**
     * 绑定token(用户,应用,租户,角色)
     * @return
     */
    @GetMapping("/bind")
    public R<String> bind(UserVO userVO) {
        String token = IdUtil.fastUUID();
        redisHelper.hshPut(WebSocketConstant.ACCESS_TOKEN, token, JacksonUtil.toJsonString(userVO));
        return R.success(token);
    }
}
