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
public class SessionVO extends UserVO{

    /**
     * websocketSession id
     */
    private String sessionId;
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

    public SessionVO(String sessionId, String user, String app, String tenant, String role, String accessToken, String brokerId) {
        super(user, app, tenant, role);
        this.sessionId = sessionId;
        this.accessToken = accessToken;
        this.brokerId = brokerId;
    }

    public SessionVO(String sessionId, String group, String brokerId) {
        this.sessionId = sessionId;
        this.group = group;
        this.brokerId = brokerId;
    }

    public UserVO toUserVO(){
        return new UserVO().setUser(getUser()).setApp(getApp()).setTenant(getTenant());
    }
}
