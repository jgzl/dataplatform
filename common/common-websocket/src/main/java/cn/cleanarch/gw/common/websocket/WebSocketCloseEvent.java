package cn.cleanarch.gw.common.websocket;

import org.springframework.context.ApplicationEvent;

/**
 * @author lihaifeng
 */
public class WebSocketCloseEvent extends ApplicationEvent {

	public WebSocketCloseEvent(WebSocket webSocket) {
		super(webSocket);
	}

}
