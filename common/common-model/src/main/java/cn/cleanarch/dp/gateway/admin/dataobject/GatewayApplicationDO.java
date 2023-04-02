package cn.cleanarch.dp.gateway.admin.dataobject;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 网关管理-应用服务表
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
@ApiModel(value = "网关管理-应用服务表",description = "")
@TableName("gateway_application")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GatewayApplicationDO extends BaseDO {

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    
    /**
    * 应用编码
    */
    @ApiModelProperty(name = "应用编码",notes = "")
    private String applicationCode ;
    
    /**
    * 应用名称
    */
    @ApiModelProperty(name = "应用名称",notes = "")
    private String applicationName ;
    
    /**
    * 部署模式;单体/微服务
    */
    @ApiModelProperty(name = "部署模式",notes = "单体/微服务")
    private String deploymentMode ;

}