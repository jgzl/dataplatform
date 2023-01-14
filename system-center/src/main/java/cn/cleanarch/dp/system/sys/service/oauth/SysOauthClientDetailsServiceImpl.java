package cn.cleanarch.dp.system.sys.service.oauth;

import cn.cleanarch.dp.common.core.constant.CacheConstants;
import cn.cleanarch.dp.system.sys.mapper.oauth.SysOauthClientDetailsMapper;
import cn.cleanarch.dp.system.sys.dataobject.oauth.SysOauthClientDetailsDO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author li7hai26@outlook.com
 * @since 2019/2/1
 */
@Service
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetailsDO>
		implements SysOauthClientDetailsService {

	/**
	 * 根据客户端id获取客户端信息
	 * @param clientId
	 * @return
	 */
	@Override
	@Cacheable(value = CacheConstants.CLIENT_DETAILS, key = "#clientId", unless = "#result == null")
	public List<SysOauthClientDetailsDO> getByClientId(String clientId) {
		return this.list(Wrappers.<SysOauthClientDetailsDO>lambdaQuery().eq(SysOauthClientDetailsDO::getClientId, clientId));
	}

	/**
	 * 通过ID删除客户端
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS, key = "#id")
	public Boolean removeClientDetailsById(String id) {
		return this.removeById(id);
	}

	/**
	 * 根据客户端信息
	 * @param clientDetails
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS, key = "#clientDetails.clientId")
	public Boolean updateClientDetailsById(SysOauthClientDetailsDO clientDetails) {
		return this.updateById(clientDetails);
	}

	/**
	 * 清除客户端缓存
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS, allEntries = true)
	public void clearClientCache() {

	}

}
