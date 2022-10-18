package cn.cleanarch.dp.system.vo;

import cn.cleanarch.dp.common.model.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("系统管理-错误码分页 Request VO")
@Data
@ToString(callSuper = true)
public class SysErrorCodePageReqVO extends PageParam {

    @ApiModelProperty(value = "错误码类型", example = "1", notes = "参见 ErrorCodeTypeEnum 枚举类")
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
