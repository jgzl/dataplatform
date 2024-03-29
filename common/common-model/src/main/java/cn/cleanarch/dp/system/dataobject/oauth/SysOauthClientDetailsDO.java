package cn.cleanarch.dp.system.dataobject.oauth;

import cn.cleanarch.dp.common.model.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @author li7hai26@outlook.com
 * @since 2019/2/1
 */
@TableName("sys_oauth_client_details")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysOauthClientDetailsDO extends BaseDO {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户端ID
	 */
	@NotBlank(message = "client_id 不能为空")
	private String clientId;

	/**
	 * 客户端密钥
	 */
	@NotBlank(message = "client_secret 不能为空")
	private String clientSecret;

	/**
	 * 资源ID
	 */
	private String resourceIds;

	/**
	 * 作用域
	 */
	@NotBlank(message = "scope 不能为空")
	private String scope;

	/**
	 * 授权方式（A,B,C）
	 */
	private String authorizedGrantTypes;

	/**
	 * 回调地址
	 */
	private String webServerRedirectUri;

	/**
	 * 权限
	 */
	private String authorities;

	/**
	 * 请求令牌有效时间
	 */
	private Integer accessTokenValidity;

	/**
	 * 刷新令牌有效时间
	 */
	private Integer refreshTokenValidity;

	/**
	 * 扩展信息
	 */
	private String additionalInformation;

	/**
	 * 是否自动放行
	 */
	private String autoapprove;

}
