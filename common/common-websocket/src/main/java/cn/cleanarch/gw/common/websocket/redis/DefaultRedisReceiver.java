package cn.cleanarch.gw.message.configuration.websocket.redis;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.cleanarch.gw.message.configuration.websocket.WebSocketManager;
import cn.cleanarch.gw.message.configuration.websocket.redis.action.Action;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;


/**
 * redis消息订阅者
 *
 * @author lihaifeng
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultRedisReceiver implements RedisReceiver {

	private final ApplicationContext applicationContext;

	/**
	 * 此方法会被反射调用
	 */
	@Override
	public void receiveMessage(String message) {
		log.info(message);
		JSONObject object = JSONUtil.parseObj(message);
		if (!object.containsKey(Action.ACTION)) {
			return;
		}
		String actionName = object.getStr(Action.ACTION);
		Action action = getAction(actionName);
		action.doMessage(getWebSocketManager(), object);
	}

	protected Action getAction(String actionName) {
		return applicationContext.getBean(actionName, Action.class);
	}

	protected WebSocketManager getWebSocketManager() {
		return applicationContext.getBean(WebSocketManager.WEBSOCKET_MANAGER_NAME, WebSocketManager.class);
	}
}