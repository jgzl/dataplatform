package cn.cleanarch.gw.message.controller;

import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.core.utils.JacksonUtil;
import cn.cleanarch.gw.common.redis.RedisHelper;
import cn.cleanarch.gw.common.websocket.constant.WebSocketConstant;
import cn.cleanarch.gw.common.websocket.vo.UserVO;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证控制器
 * @author li7hai26@gmail.com
 */
@RestController
public class AuthController {
    @Autowired
    private RedisHelper redisHelper;

    /**
     * 登录绑定token
     * @return
     */
    @GetMapping("/login/{user}")
    public R<UserVO> login(@PathVariable String user) {
        String token = IdUtil.fastUUID();
        UserVO userVO = new UserVO();
        userVO.setUser(user);
        userVO.setRole("default");
        userVO.setTenant("default");
        redisHelper.hshPut(WebSocketConstant.ACCESS_TOKEN, token, JacksonUtil.toJsonString(userVO));
        return R.success(userVO);
    }
}
