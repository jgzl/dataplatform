package cn.cleanarch.dp.infra.dataobject.db;

import cn.cleanarch.dp.common.data.handler.EncryptTypeHandler;
import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 数据源配置
 *
 * @author li7hai26@gmail.com
 */
@TableName(value = "infra_data_source_config", autoResultMap = true)
@Data
public class DataSourceConfigDO extends BaseDO {

    /**
     * 主键编号 - Master 数据源
     */
    public static final String ID_MASTER = "0";

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 连接名
     */
    private String name;

    /**
     * 数据源连接
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String password;

}
