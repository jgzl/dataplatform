package cn.cleanarch.dp.gateway.admin.vo;

import cn.cleanarch.dp.common.core.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网关管理-元数据管理;
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
@ApiModel(value = "网关管理-元数据管理",description = "")
@Data
public class GatewayMetadataVO {

    /**
    * 应用编码-提供服务方
    */
    @Excel(name = "应用编码")
    @ApiModelProperty(name = "应用编码-提供服务方",notes = "")
    private String applicationCode;
    
    /**
    * 请求类型|GET
    */
    @Excel(name = "请求类型")
    @ApiModelProperty(name = "请求类型",notes = "")
    private String method ;
    
    /**
    * 请求路径
    */
    @Excel(name = "请求路径")
    @ApiModelProperty(name = "请求路径",notes = "")
    private String path ;
    
    /**
    * 接口描述
    */
    @Excel(name = "接口描述")
    @ApiModelProperty(name = "接口描述",notes = "")
    private String description ;
    
    /**
    * RPC接口类型(http)
    */
    @Excel(name = "RPC接口类型")
    @ApiModelProperty(name = "RPC接口类型(http)",notes = "")
    private String rpcType ;
    
    /**
    * RPC扩展参数
    */
    @Excel(name = "RPC扩展参数")
    @ApiModelProperty(name = "RPC扩展参数",notes = "")
    private String rpcExtra ;
    
    /**
    * 标签
    */
    @Excel(name = "标签")
    @ApiModelProperty(name = "标签",notes = "")
    private String tag ;

}