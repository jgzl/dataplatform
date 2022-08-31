package cn.cleanarch.gw.common.model.system.vo;

import lombok.Data;

import java.util.List;

@Data
public class LoginUserInfo {
    private String dashboard = "0";
    private List<String> role;
    private String userId;
    private String userName;
}