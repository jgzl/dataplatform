package cn.cleanarch.gw.gateway.admin.system.controller;

import cn.cleanarch.gw.common.core.model.R;
import cn.cleanarch.gw.common.model.system.domain.SysDept;
import cn.cleanarch.gw.common.model.system.domain.SysDeptRelation;
import cn.cleanarch.gw.gateway.admin.system.service.SysDeptRelationService;
import cn.cleanarch.gw.gateway.admin.system.service.SysDeptService;
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
    public R<SysDept> getById(@PathVariable Integer id) {
        return R.success(sysDeptService.getById(id));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R<List<Tree<Long>>> getTree() {
        return R.success(sysDeptService.selectTree());
    }

    /**
     * 添加
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PostMapping
    //@PreAuthorize("@pms.hasPermission('sys_dept_add')")
    public R<Boolean> save(@Valid @RequestBody SysDept sysDept) {
        return R.success(sysDeptService.saveDept(sysDept));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("@pms.hasPermission('sys_dept_del')")
    public R<Boolean> removeById(@PathVariable Long id) {
        return R.success(sysDeptService.removeDeptById(id));
    }

    /**
     * 编辑
     *
     * @param sysDept 实体
     * @return success/false
     */
    @PutMapping
    //@PreAuthorize("@pms.hasPermission('sys_dept_edit')")
    public R<Boolean> update(@Valid @RequestBody SysDept sysDept) {
        sysDept.setUpdateTime(LocalDateTime.now());
        return R.success(sysDeptService.updateDeptById(sysDept));
    }

    /**
     * 查收子级列表
     *
     * @param deptId 部门id
     * @return 返回子级
     */
    @GetMapping(value = "/getDescendantList/{deptId}")
    public R<List<SysDeptRelation>> getDescendantList(@PathVariable Integer deptId) {
        return R.success(
                relationService.list(Wrappers.<SysDeptRelation>lambdaQuery().eq(SysDeptRelation::getAncestor, deptId)));
    }

}
