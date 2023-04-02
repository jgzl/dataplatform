package cn.cleanarch.dp.infra.mapper.codegen;


import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.common.data.query.LambdaQueryWrapperX;
import cn.cleanarch.dp.common.model.PageParam;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenTableDO;
import cn.cleanarch.dp.infra.vo.codegen.table.CodegenTablePageReqVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface CodegenTableMapper extends ExtendBaseMapper<CodegenTableDO> {

    default CodegenTableDO selectByTableNameAndDataSourceConfigId(String tableName, String dataSourceConfigId) {
        return selectOne(CodegenTableDO::getTableName, tableName,
                CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

    @Override
    default PageResult<CodegenTableDO> selectPage(PageParam pageParam, Wrapper<CodegenTableDO> queryWrapper) {
        return ExtendBaseMapper.super.selectPage(pageParam, queryWrapper);
    }

    default PageResult<CodegenTableDO> selectPage(CodegenTablePageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<CodegenTableDO>()
                .likeIfPresent(CodegenTableDO::getTableName, pageReqVO.getTableName())
                .likeIfPresent(CodegenTableDO::getTableComment, pageReqVO.getTableComment())
                .betweenIfPresent(CodegenTableDO::getCreateTime, pageReqVO.getCreateTime()));
    }

    default List<CodegenTableDO> selectListByDataSourceConfigId(String dataSourceConfigId) {
        return selectList(CodegenTableDO::getDataSourceConfigId, dataSourceConfigId);
    }

}
