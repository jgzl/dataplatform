package cn.cleanarch.dp.common.websocket.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * description
 *
 * @author shuangfei.zhu@hand-china.com 2019/06/14 9:45
 */
@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SessionVO {

    /**
     * websocketSession id
     */
    private String sessionId;
    /**
     * 用户Id
     */
    private String user;
    /**
     * 当前租户
     */
    private String tenant;
    /**
     * 当前角色
     */
    private String role;
    /**
     * token
     */
    private String accessToken;
    /**
     * brokerId
     */
    private String brokerId;


    /**
     * group
     */
    private String group;

    public SessionVO() {

    }

    public SessionVO(String sessionId, String user, String tenant, String role, String accessToken, String brokerId) {
        this.sessionId = sessionId;
        this.user = user;
        this.tenant = tenant;
        this.role = role;
        this.accessToken = accessToken;
        this.brokerId = brokerId;
    }

    public SessionVO(String sessionId, String group, String brokerId) {
        this.sessionId = sessionId;
        this.group = group;
        this.brokerId = brokerId;
    }

}
