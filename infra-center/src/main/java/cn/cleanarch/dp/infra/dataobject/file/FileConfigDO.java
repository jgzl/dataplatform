package cn.cleanarch.dp.infra.dataobject.file;

import cn.cleanarch.dp.common.model.BaseDO;
import cn.cleanarch.dp.common.file.core.client.FileClientConfig;
import cn.cleanarch.dp.common.file.core.enums.FileStorageEnum;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

/**
 * 文件配置表
 *
 * @author 芋道源码
 */
@TableName(value = "infra_file_config", autoResultMap = true)
@KeySequence("infra_file_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileConfigDO extends BaseDO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 配置名
     */
    private String name;
    /**
     * 存储器
     *
     * 枚举 {@link FileStorageEnum}
     */
    private Integer storage;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否为主配置
     *
     * 由于我们可以配置多个文件配置，默认情况下，使用主配置进行文件的上传
     */
    private Boolean master;

    /**
     * 支付渠道配置
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private FileClientConfig config;

}
