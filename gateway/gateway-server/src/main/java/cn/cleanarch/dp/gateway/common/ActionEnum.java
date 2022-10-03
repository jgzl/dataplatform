package cn.cleanarch.dp.gateway.common;

/**
 * 操作类型枚举
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/17
 */
public enum ActionEnum {

    CREATE(1, "保存"),
    UPDATE(2, "更新"),
    DELETE(3, "删除"),
    ;

    Integer code;
    String msg;

    ActionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsg(Integer code) {
        for (ActionEnum value : ActionEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.msg;
            }
        }
        return null;
    }
}
