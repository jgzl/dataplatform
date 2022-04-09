package com.github.gw.common.model.errorcode.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "管理后台 - 错误码 Excel 导出 Request VO", description = "参数和 InfErrorCodePageReqVO 是一致的")
@Data
public class ErrorCodeExportReqVO {

    @ApiModelProperty(value = "错误码类型", example = "1")
    private Integer type;

    @ApiModelProperty(value = "应用名", example = "dashboard")
    private String applicationName;

    @ApiModelProperty(value = "错误码编码", example = "1234")
    private Integer code;

    @ApiModelProperty(value = "错误码错误提示", example = "帅气")
    private String message;

    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    @ApiModelProperty(value = "开始创建时间")
    private Date beginCreateTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    @ApiModelProperty(value = "结束创建时间")
    private Date endCreateTime;

}
