package cn.cleanarch.dp.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    /**
     * 左侧菜单
     */
    LEFT_MENU("0", "left"),

    /**
     * 按钮
     */
    BUTTON("1", "button"),

    /**
     * iframe
     */
    IFRAME("2", "iframe"),

    /**
     * 外链
     */
    OUT_LINK("3", "outLink"),

    /**
     * 内链
     */
    IN_LINK("4", "inLink"),

    ;
    /**
     * 类型
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    public static Boolean isFrontendComponent(String type) {
        if (LEFT_MENU.getType().equals(type) || IN_LINK.getType().equals(type)) {
            return true;
        }
        return false;
    }
}
