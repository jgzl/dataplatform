package cn.cleanarch.gw.common.websocket.vo;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;

/**
 * description
 *
 * @author shuangfei.zhu@hand-china.com 2019/04/19 15:49
 */
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgVO {

    private String user;
    private String group;
    /**
     * websocketSession Id
     */
    private String sessionId;
    /**
     * 服务
     */
    private String service;
    /**
     * 话题
     */
    private String key;
    /**
     * 消息内容
     */
    private String message;
    /**
     * 发送方式
     */
    private String type;
    /**
     * 二进制数据
     */
    private String data;
    /**
     * 消息来源节点id
     */
    private String brokerId;

    public String getUser() {
        return user;
    }

    public MsgVO setUser(String user) {
        this.user = user;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public MsgVO setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public MsgVO setSessionId(String sessionId) {
        this.sessionId = sessionId;
        return this;
    }

    public String getService() {
        return service;
    }

    public MsgVO setService(String service) {
        this.service = service;
        return this;
    }

    public String getKey() {
        return key;
    }

    public MsgVO setKey(String key) {
        this.key = key;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MsgVO setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getType() {
        return type;
    }

    public MsgVO setType(String type) {
        this.type = type;
        return this;
    }

    public byte[] getData() {
        if (data == null) {
            return null;
        }
        return data.getBytes();
    }

    public MsgVO setData(byte[] data) {
        if (data == null) {
            this.data = null;
            return this;
        }
        this.data = new String(data, StandardCharsets.UTF_8);
        return this;
    }

    public String getBrokerId() {
        return brokerId;
    }

    public MsgVO setBrokerId(String brokerId) {
        this.brokerId = brokerId;
        return this;
    }
}
