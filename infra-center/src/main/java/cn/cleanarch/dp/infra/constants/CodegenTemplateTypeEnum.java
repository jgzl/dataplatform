package cn.cleanarch.dp.infra.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码生成模板类型
 *
 * @author li7hai26@gmail.com
 */
@AllArgsConstructor
@Getter
public enum CodegenTemplateTypeEnum {

    CRUD(1), // 单表（增删改查）
    TREE(2), // 树表（增删改查）
    ;

    /**
     * 类型
     */
    private final Integer type;

}
