package cn.cleanarch.dp.infra.vo.codegen.table;

import cn.cleanarch.dp.common.model.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("管理后台 - 表定义分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CodegenTablePageReqVO extends PageParam {

    @ApiModelProperty(value = "表名称", example = "yudao", notes = "模糊匹配")
    private String tableName;

    @ApiModelProperty(value = "表描述", example = "芋道", notes = "模糊匹配")
    private String tableComment;

    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date[] createTime;

}
