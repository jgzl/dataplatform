package cn.cleanarch.gw.common.model.gateway.domain;

import cn.cleanarch.gw.common.model.base.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 路由
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GatewayAccessConf extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 网关访问key
     */
    private String apiKey;

    /**
     * 网关访问secret
     */
    private String apiSecret;

    /**
     * 访问系统
     */
    @TableField(value = "`system`")
    private String system;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private String status;

}