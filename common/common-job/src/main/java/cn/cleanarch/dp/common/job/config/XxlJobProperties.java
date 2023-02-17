package cn.cleanarch.dp.common.job.config;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * xxl-job配置
 *
 * @author li7hai26@outlook.com
 * @date 2020/9/14
 */
@Data
@ConfigurationProperties(prefix = XxlJobProperties.XXL_JOB_PREFIX)
public class XxlJobProperties {

	public static final String XXL_JOB_PREFIX = CommonConstants.CONFIGURATION_PREFIX+".xxl.job";

	@NestedConfigurationProperty
	private XxlAdminProperties admin = new XxlAdminProperties();

	@NestedConfigurationProperty
	private XxlExecutorProperties executor = new XxlExecutorProperties();

}
