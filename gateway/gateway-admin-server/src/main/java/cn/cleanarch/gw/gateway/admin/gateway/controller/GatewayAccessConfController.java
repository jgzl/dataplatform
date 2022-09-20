package cn.cleanarch.gw.gateway.admin.gateway.controller;

import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.model.gateway.domain.GatewayAccessConf;
import cn.cleanarch.gw.common.model.gateway.vo.GatewayAccessConfVo;
import cn.cleanarch.gw.gateway.admin.gateway.service.GatewayAccessConfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
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
public class GatewayAccessConfController {

    private final GatewayAccessConfService service;

    /**
     * 分页获取当前定义的信息
     * @return
     */
    @GetMapping("/page")
    public R<IPage<GatewayAccessConfVo>> pageListRoutes(Page page, GatewayAccessConfVo vo) {
        return R.success(service.page(page, new QueryWrapper<GatewayAccessConf>(vo)));
    }

    /**
     * 分页获取当前定义的信息
     *
     * @return
     */
    @GetMapping
    public R<List<GatewayAccessConf>> listRoutes() {
        return R.success(service.list());
    }

    /**
     * 新增
     *
     * @param vo 定义
     * @return
     */
    @PostMapping
    public R<GatewayAccessConf> save(@RequestBody GatewayAccessConf vo) {
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
    public R<GatewayAccessConf> updateById(@RequestBody GatewayAccessConf vo) {
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
    public R<Boolean> updateUserForLockFlag(@Valid @RequestBody GatewayAccessConfVo vo) {
        return R.success(service.updateStatus(vo));
    }
}
