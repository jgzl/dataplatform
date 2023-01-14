package cn.cleanarch.dp.system.sys.vo.user;

import lombok.Data;

import java.time.Instant;

/**
 * token vo
 * @author li7hai26@outlook.com
 */
@Data
public class TokenVO {

	private String id;

	private Long userId;

	private String clientId;

	private String username;

	private String accessToken;

	private Instant issuedAt;

	private Instant expiresAt;

}