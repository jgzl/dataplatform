package cn.cleanarch.dp.system.infra.configuration;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@ConfigurationProperties(prefix = CommonConstants.CONFIGURATION_PREFIX+".codegen")
@Validated
@Data
public class CodegenProperties {

    /**
     * 生成的 Java 代码的基础包
     */
    @NotNull(message = "Java 代码的基础包不能为空")
    private String basePackage = "cn.cleanarch.dp.system";

    /**
     * 数据库名数组
     */
    private Collection<String> dbSchemas;

}
