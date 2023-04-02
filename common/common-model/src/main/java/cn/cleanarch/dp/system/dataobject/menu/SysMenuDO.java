package cn.cleanarch.dp.system.dataobject.menu;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单权限表
 *
 * @author li7hai26@gmail.com
 * @since 2021-11-08
 */
@TableName("sys_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SysMenuDO extends BaseDO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 菜单权限标识
     */
    private String permission;

    /**
     * 父菜单ID
     */
    @NotNull(message = "菜单父ID不能为空")
    private String parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 前端路由标识路径，默认和 comment 保持一致 过期
     */
    private String path;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @NotNull(message = "菜单类型不能为空")
    private String type;

    /**
     * 路由缓冲
     */
    private Boolean keepAlive;

    /**
     * 视图
     */
    private String component;

    /**
     * 标签
     */
    private String tag;

}