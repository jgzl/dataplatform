package cn.cleanarch.dp.system.sys.controller.oauth;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.oauth.annotation.Inner;
import cn.cleanarch.dp.system.sys.dataobject.oauth.SysOauthClientDetailsDO;
import cn.cleanarch.dp.system.sys.service.oauth.SysOauthClientDetailsService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author li7hai26@outlook.com
 * @since 2018-05-15
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/client")
public class SysOauthClientDetailsController {

	private final SysOauthClientDetailsService sysOauthClientDetailsService;

	/**
	 * 通过clientId查询
	 * @param clientId 客户端id
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{clientId}")
	@PreAuthorize("@pms.hasPermission('system:sys-oauth-client:query')")
	public R<List<SysOauthClientDetailsDO>> getByClientId(@PathVariable String clientId) {
		List<SysOauthClientDetailsDO> result = sysOauthClientDetailsService.getByClientId(clientId);
		return R.success(result);
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('system:sys-oauth-client:query')")
	public R<IPage<SysOauthClientDetailsDO>> getOauthClientDetailsPage(Page page,
			SysOauthClientDetailsDO sysOauthClientDetails) {
		return R.success(sysOauthClientDetailsService.page(page, Wrappers.query(sysOauthClientDetails)));
	}

	/**
	 * 添加终端
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@PostMapping
	@PreAuthorize("@pms.hasPermission('system:sys-oauth-client:create')")
	public R<Boolean> add(@Valid @RequestBody SysOauthClientDetailsDO sysOauthClientDetails) {
		return R.success(sysOauthClientDetailsService.save(sysOauthClientDetails));
	}

	/**
	 * 删除终端
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('system:sys-oauth-client:delete')")
	public R<Boolean> removeById(@PathVariable String id) {
		return R.success(sysOauthClientDetailsService.removeClientDetailsById(id));
	}

	/**
	 * 编辑终端
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@PutMapping
	@PreAuthorize("@pms.hasPermission('system:sys-oauth-client:update')")
	public R<Boolean> update(@Valid @RequestBody SysOauthClientDetailsDO sysOauthClientDetails) {
		return R.success(sysOauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails));
	}

	/**
	 * 清除终端缓存
	 * @return
	 */
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('system:sys-oauth-client:delete')")
	public R clearClientCache() {
		sysOauthClientDetailsService.clearClientCache();
		return R.success();
	}

	@Inner(false)
	@GetMapping("/getClientDetailsById/{clientId}")
	public R getClientDetailsById(@PathVariable String clientId) {
		log.info("客户端id为:{}",clientId);
		return R.success(sysOauthClientDetailsService.getOne(
				Wrappers.<SysOauthClientDetailsDO>lambdaQuery().eq(SysOauthClientDetailsDO::getClientId, clientId), true));
	}

}
