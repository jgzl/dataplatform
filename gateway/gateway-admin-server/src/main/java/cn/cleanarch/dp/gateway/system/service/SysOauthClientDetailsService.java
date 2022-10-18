package cn.cleanarch.dp.gateway.system.service;

import cn.cleanarch.dp.system.domain.SysOauthClientDetailsDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author li7hai26@outlook.com
 * @since 2019/2/1
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetailsDO> {

	/**
	 * 通过ID删除客户端
	 * @param id
	 * @return
	 */
	Boolean removeClientDetailsById(String id);

	/**
	 * 修改客户端信息
	 * @param sysOauthClientDetails
	 * @return
	 */
	Boolean updateClientDetailsById(SysOauthClientDetailsDO sysOauthClientDetails);

	/**
	 * 清除客户端缓存
	 */
	void clearClientCache();

	/**
	 * 根据客户端id获取客户端信息
	 * @param clientId
	 * @return
	 */
	List<SysOauthClientDetailsDO> getByClientId(String clientId);
}
