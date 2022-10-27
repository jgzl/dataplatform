package cn.cleanarch.dp.common.websocket.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * websocket用户信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserVO {
    /**
     * 用户
     */
    @NotBlank
    private String user;
    /**
     * 应用|message-center
     */
    private String app = "message-center";
    /**
     * 租户|tenant-id-0
     */
    private String tenant = "tenant-id-0";
    /**
     * 角色
     */
    private String role;
}
