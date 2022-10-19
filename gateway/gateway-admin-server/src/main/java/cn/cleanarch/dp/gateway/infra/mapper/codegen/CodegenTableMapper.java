package cn.cleanarch.dp.gateway.infra.mapper.codegen;


import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.common.data.query.LambdaQueryWrapperX;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenTableDO;
import cn.cleanarch.dp.infra.vo.codegen.table.CodegenTablePageReqVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CodegenTableMapper extends ExtendBaseMapper<CodegenTableDO> {

    default CodegenTableDO selectByTableNameAndDataSourceConfigId(String tableName, Long dataSourceConfigId) {
        return selectOne(CodegenTableDO::getTableName, tableName,
                CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

    default IPage<CodegenTableDO> selectPage(CodegenTablePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<CodegenTableDO>()
                .likeIfPresent(CodegenTableDO::getTableName, pageReqVO.getTableName())
                .likeIfPresent(CodegenTableDO::getTableComment, pageReqVO.getTableComment())
                .betweenIfPresent(CodegenTableDO::getCreateTime, pageReqVO.getCreateTime()));
    }

    default List<CodegenTableDO> selectListByDataSourceConfigId(Long dataSourceConfigId) {
        return selectList(CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

}
