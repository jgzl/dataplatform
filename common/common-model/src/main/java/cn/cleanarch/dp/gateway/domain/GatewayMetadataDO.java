package cn.cleanarch.dp.gateway.domain;

import cn.cleanarch.dp.common.model.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 网关管理-元数据管理
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
@ApiModel(value = "网关管理-元数据管理",description = "")
@TableName("gateway_metadata")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GatewayMetadataDO extends BaseDO {
    
    /**
    * 应用编码-提供服务方
    */
    @ApiModelProperty(name = "应用编码-提供服务方",notes = "")
    private String applicationCode;
    
    /**
    * 请求类型|GET
    */
    @ApiModelProperty(name = "请求类型",notes = "")
    private String method ;
    
    /**
    * 请求路径
    */
    @ApiModelProperty(name = "请求路径",notes = "")
    private String path ;
    
    /**
    * 接口描述
    */
    @ApiModelProperty(name = "接口描述",notes = "")
    private String description ;
    
    /**
    * RPC接口类型(http,dubbo)
    */
    @ApiModelProperty(name = "RPC接口类型(http,dubbo)",notes = "")
    private String rpcType ;
    
    /**
    * RPC扩展参数
    */
    @ApiModelProperty(name = "RPC扩展参数",notes = "")
    private String rpcExtra ;
    
    /**
    * 接口标签/请求标签
    */
    @ApiModelProperty(name = "接口标签/请求标签",notes = "")
    private String tag ;

}