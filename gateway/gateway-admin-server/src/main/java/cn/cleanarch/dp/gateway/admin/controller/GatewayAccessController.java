package cn.cleanarch.dp.gateway.admin.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.gateway.admin.service.GatewayAccessService;
import cn.cleanarch.dp.gateway.domain.GatewayAccessDO;
import cn.cleanarch.dp.gateway.vo.GatewayAccessVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 网关管理-网关访问模块
 *
 * @author li7hai26@gmail.com
 * @date 2018-11-06 10:17:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/gateway/access")
public class GatewayAccessController {

    private final GatewayAccessService service;

    /**
     * 分页获取当前定义的信息
     * @return
     */
    @GetMapping("/page")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-access:query')")
    public R<IPage<GatewayAccessVO>> pageListRoutes(Page page, GatewayAccessVO vo) {
        return R.success(service.page(page, new QueryWrapper<GatewayAccessDO>(vo)));
    }

    /**
     * 分页获取当前定义的信息
     *
     * @return
     */
    @GetMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-access:query')")
    public R<List<GatewayAccessDO>> listRoutes() {
        return R.success(service.list());
    }

    /**
     * 新增
     *
     * @param vo 定义
     * @return
     */
    @PostMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-access:save')")
    public R<GatewayAccessDO> save(@RequestBody GatewayAccessDO vo) {
        service.save(vo);
        return R.success(vo);
    }

    /**
     * 修改
     *
     * @param vo 定义
     * @return
     */
    @PutMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-access:update')")
    public R<GatewayAccessDO> updateById(@RequestBody GatewayAccessDO vo) {
        service.updateById(vo);
        return R.success(vo);
    }

    /**
     * 删除
     *
     * @param id 路由id
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-access:delete')")
    public R<Void> deleteItem(@PathVariable String id) {
        service.deleteItem(id);
        return R.success();
    }

    /**
     * 更新状态-禁用状态
     *
     * @param vo 用户信息
     * @return R
     */
    @PutMapping("/status")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-access:update')")
    public R<Boolean> updateUserForLockFlag(@Valid @RequestBody GatewayAccessVO vo) {
        return R.success(service.updateStatus(vo));
    }
}
