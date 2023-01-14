package cn.cleanarch.dp.tool.service;

import cn.cleanarch.dp.tool.metadata.dto.SqlConsoleDto;
import cn.cleanarch.dp.tool.metadata.vo.SqlConsoleVo;

import java.util.List;

public interface SqlConsoleService {
    
    List<SqlConsoleVo> sqlRun(SqlConsoleDto sqlConsoleDto);

    void sqlStop(SqlConsoleDto sqlConsoleDto);
}
