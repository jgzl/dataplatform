package cn.cleanarch.dp.gateway.system.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.gateway.system.service.SysDeptRelationService;
import cn.cleanarch.dp.gateway.system.service.SysDeptService;
import cn.cleanarch.dp.system.domain.SysDeptDO;
import cn.cleanarch.dp.system.domain.SysDeptRelationDO;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门管理模块
 *
 * @author li7hai26@gmail.com
 * @since 2018-01-20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/system/dept")
public class SysDeptController {

    private final SysDeptRelationService relationService;

    private final SysDeptService sysDeptService;

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysDept
     */
    @GetMapping("/{id}")
    public R<SysDeptDO> getById(@PathVariable Integer id) {
        return R.success(sysDeptService.getById(id));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R<List<Tree<String>>> getTree() {
        return R.success(sysDeptService.selectTree());
    }

    /**
     * 添加
     *
     * @param sysDeptDO 实体
     * @return success/false
     */
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('sys_dept_add')")
    public R<Boolean> save(@Valid @RequestBody SysDeptDO sysDeptDO) {
        return R.success(sysDeptService.saveDept(sysDeptDO));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("@pms.hasPermission('sys_dept_del')")
    public R<Boolean> removeById(@PathVariable String id) {
        return R.success(sysDeptService.removeDeptById(id));
    }

    /**
     * 编辑
     *
     * @param sysDeptDO 实体
     * @return success/false
     */
    @PutMapping
    //@PreAuthorize("@pms.hasPermission('sys_dept_edit')")
    public R<Boolean> update(@Valid @RequestBody SysDeptDO sysDeptDO) {
        sysDeptDO.setUpdateTime(LocalDateTime.now());
        return R.success(sysDeptService.updateDeptById(sysDeptDO));
    }

    /**
     * 查收子级列表
     *
     * @param deptId 部门id
     * @return 返回子级
     */
    @GetMapping(value = "/getDescendantList/{deptId}")
    public R<List<SysDeptRelationDO>> getDescendantList(@PathVariable Integer deptId) {
        return R.success(
                relationService.list(Wrappers.<SysDeptRelationDO>lambdaQuery().eq(SysDeptRelationDO::getAncestor, deptId)));
    }

}
