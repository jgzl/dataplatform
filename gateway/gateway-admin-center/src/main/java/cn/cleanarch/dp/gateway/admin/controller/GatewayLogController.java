package cn.cleanarch.dp.gateway.admin.controller;

import cn.cleanarch.dp.gateway.admin.service.GatewayLogService;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayLogDO;
import cn.cleanarch.dp.gateway.admin.vo.GatewayLogVO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 网关管理-网关日志模块
 *
 * @author li7hai26@gmail.com
 */
@Slf4j
@RestController
@RequestMapping("/gateway/log")
public class GatewayLogController {

    @Autowired
    private GatewayLogService service;

    /**
     * 获取所有网关日志
     *
     * @return
     */
    @GetMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-log:query')")
    public R<List<GatewayLogDO>> findAll() {
        List<GatewayLogDO> result = service.findAll();
        return R.success(result);
    }

    /**
     * 根据ID集合获取网关日志
     *
     * @param ids id集合字符串
     * @return
     */
    @GetMapping("/{ids}")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-log:query')")
    public R<List<GatewayLogDO>> findAllById(@PathVariable String ids) {
        if (StrUtil.isBlank(ids)) {
            return R.success();
        }
        List<String> idList = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        List<GatewayLogDO> result = service.findAllById(idList);
        return R.success(result);
    }

    /**
     * 批量保存网关日志
     *
     * @param list 网关请求对象集合
     * @return
     */
    @PostMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-log:create')")
    public R<List<GatewayLogDO>> saveAll(@RequestBody List<GatewayLogDO> list) {
        if (CollUtil.isEmpty(list)) {
            list = ListUtil.toList();
        }
        for (GatewayLogDO gatewayLog : list) {
            gatewayLog.setId(IdUtil.fastUUID());
        }
        List<GatewayLogDO> result = service.saveAll(list);
        return R.success(result);
    }

    /**
     * 删除所有网关日志
     *
     * @return
     */
    @DeleteMapping
    @PreAuthorize("@pms.hasPermission('gateway:gateway-log:delete')")
    public R<Void> deleteAll() {
        service.deleteAll();
        return R.success();
    }

    /**
     * 根据ID集合删除网关日志
     *
     * @param ids id集合字符串
     * @return
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-log:delete')")
    public R<Void> deleteAllByIds(@PathVariable String ids) {
        if (StrUtil.isBlank(ids)) {
            return R.success();
        }
        List<String> idList = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        service.deleteAllById(idList);
        return R.success();
    }

    /**
     * 根据参数从数据仓库中获取网关日志
     * @param page 分页参数
     * @param gatewayRequestLog 网关请求对象
     * @return
     */
    @GetMapping("/search")
    @PreAuthorize("@pms.hasPermission('gateway:gateway-log:query')")
    public R<IPage<GatewayLogVO>> search(Page<GatewayLogVO> page, GatewayLogVO gatewayRequestLog) {
        Page<GatewayLogVO> result = service.getByGatewayRequestLog(page,gatewayRequestLog);
        return R.success(result);
    }
}