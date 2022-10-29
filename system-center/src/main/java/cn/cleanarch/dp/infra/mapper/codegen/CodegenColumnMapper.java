package cn.cleanarch.dp.infra.mapper.codegen;

import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.common.data.query.LambdaQueryWrapperX;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenColumnDO;

import java.util.List;

public interface CodegenColumnMapper extends ExtendBaseMapper<CodegenColumnDO> {

    default List<CodegenColumnDO> selectListByTableId(String tableId) {
        return selectList(new LambdaQueryWrapperX<CodegenColumnDO>()
                .eq(CodegenColumnDO::getTableId, tableId)
                .orderByAsc(CodegenColumnDO::getId));
    }

    default void deleteListByTableId(String tableId) {
        delete(new LambdaQueryWrapperX<CodegenColumnDO>()
                .eq(CodegenColumnDO::getTableId, tableId));
    }

}
