package cn.cleanarch.dp.system.infra.vo.codegen.table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("管理后台 - 数据库的表定义 Response VO")
@Data
public class DatabaseTableRespVO {

    @ApiModelProperty(value = "表名称", required = true, example = "yuanma")
    private String name;

    @ApiModelProperty(value = "表描述", required = true, example = "li7hai26@gmail.com")
    private String comment;

}
