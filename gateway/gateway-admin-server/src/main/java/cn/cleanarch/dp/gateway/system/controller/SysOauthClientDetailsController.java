package cn.cleanarch.dp.gateway.system.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.gateway.system.service.SysOauthClientDetailsService;
import cn.cleanarch.dp.system.domain.SysOauthClientDetailsDO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class SysOauthClientDetailsController {

	private final SysOauthClientDetailsService sysOauthClientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId 客户端id
	 * @return SysOauthClientDetails
	 */
	@GetMapping("/{clientId}")
	public R<List<SysOauthClientDetailsDO>> getByClientId(@PathVariable String clientId) {
		return R.success(sysOauthClientDetailsService
				.list(Wrappers.<SysOauthClientDetailsDO>lambdaQuery().eq(SysOauthClientDetailsDO::getClientId, clientId)));
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
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
//	@PreAuthorize("@pms.hasPermission('sys.client.add')")
	public R<Boolean> add(@Valid @RequestBody SysOauthClientDetailsDO sysOauthClientDetails) {
		return R.success(sysOauthClientDetailsService.save(sysOauthClientDetails));
	}

	/**
	 * 删除终端
	 * @param id ID
	 * @return success/false
	 */
	@DeleteMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('sys.client.del')")
	public R<Boolean> removeById(@PathVariable String id) {
		return R.success(sysOauthClientDetailsService.removeClientDetailsById(id));
	}

	/**
	 * 编辑终端
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('sys.client.edit')")
	public R<Boolean> update(@Valid @RequestBody SysOauthClientDetailsDO sysOauthClientDetails) {
		return R.success(sysOauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails));
	}

	/**
	 * 清除终端缓存
	 * @return
	 */
	@DeleteMapping("/cache")
//	@PreAuthorize("@pms.hasPermission('sys.client.del')")
	public R clearClientCache() {
		sysOauthClientDetailsService.clearClientCache();
		return R.success();
	}
	
	@GetMapping("/getClientDetailsById/{clientId}")
	public R getClientDetailsById(@PathVariable String clientId) {
		return R.success(sysOauthClientDetailsService.getOne(
				Wrappers.<SysOauthClientDetailsDO>lambdaQuery().eq(SysOauthClientDetailsDO::getClientId, clientId), false));
	}

}
