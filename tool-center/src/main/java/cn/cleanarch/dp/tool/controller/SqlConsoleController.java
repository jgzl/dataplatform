package cn.cleanarch.dp.tool.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.tool.metadata.dto.SqlConsoleDto;
import cn.cleanarch.dp.tool.metadata.validate.ValidationGroups;
import cn.cleanarch.dp.tool.metadata.vo.SqlConsoleVo;
import cn.cleanarch.dp.tool.service.SqlConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sql")
public class SqlConsoleController {

    @Autowired
    private SqlConsoleService sqlConsoleService;

    @PostMapping("/run")
    public R<List<SqlConsoleVo>> sqlRun(@RequestBody @Validated SqlConsoleDto sqlConsoleDto){
        List<SqlConsoleVo> list = sqlConsoleService.sqlRun(sqlConsoleDto);
        return R.success(list);
    }

    @PostMapping("/stop")
    public R<Void> sqlStop(@RequestBody @Validated({ValidationGroups.Other.class}) SqlConsoleDto sqlConsoleDto){
        sqlConsoleService.sqlStop(sqlConsoleDto);
        return R.success();
    }
}
