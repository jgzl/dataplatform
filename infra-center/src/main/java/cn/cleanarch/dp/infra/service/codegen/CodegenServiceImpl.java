package cn.cleanarch.dp.infra.service.codegen;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.common.core.utils.collection.CollectionUtils;
import cn.cleanarch.dp.infra.vo.codegen.CodegenCreateListReqVO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenUpdateReqVO;
import cn.cleanarch.dp.infra.vo.codegen.table.CodegenTablePageReqVO;
import cn.cleanarch.dp.infra.vo.codegen.table.DatabaseTableRespVO;
import cn.cleanarch.dp.infra.convert.codegen.CodegenConvert;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenColumnDO;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenTableDO;
import cn.cleanarch.dp.infra.mapper.codegen.CodegenColumnMapper;
import cn.cleanarch.dp.infra.mapper.codegen.CodegenTableMapper;
import cn.cleanarch.dp.infra.enums.codegen.CodegenSceneEnum;
import cn.cleanarch.dp.infra.service.codegen.inner.CodegenBuilder;
import cn.cleanarch.dp.infra.service.codegen.inner.CodegenEngine;
import cn.cleanarch.dp.infra.service.db.DatabaseTableService;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static cn.cleanarch.dp.common.core.exception.enums.ErrorCodeConstants.*;
import static cn.cleanarch.dp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 代码生成 Service 实现类
 *
 * @author 芋道源码
 */
@Service
public class CodegenServiceImpl implements CodegenService {

    @Resource
    private DatabaseTableService databaseTableService;

    @Resource
    private CodegenTableMapper codegenTableMapper;
    @Resource
    private CodegenColumnMapper codegenColumnMapper;

    @Resource
    private CodegenBuilder codegenBuilder;
    @Resource
    private CodegenEngine codegenEngine;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> createCodegenList(String userId, CodegenCreateListReqVO reqVO) {
        List<String> ids = new ArrayList<>(reqVO.getTableNames().size());
        // 遍历添加。虽然效率会低一点，但是没必要做成完全批量，因为不会这么大量
        reqVO.getTableNames().forEach(tableName -> ids.add(createCodegen(userId, reqVO.getDataSourceConfigId(), tableName)));
        return ids;
    }

    public String createCodegen(String userId, String dataSourceConfigId, String tableName) {
        // 从数据库中，获得数据库表结构
        TableInfo tableInfo = databaseTableService.getTable(dataSourceConfigId, tableName);
        // 导入
        return createCodegen0(userId, dataSourceConfigId, tableInfo);
    }

    private String createCodegen0(String userId, String dataSourceConfigId, TableInfo tableInfo) {
        // 校验导入的表和字段非空
        validateTableInfo(tableInfo);
        // 校验是否已经存在
        if (codegenTableMapper.selectByTableNameAndDataSourceConfigId(tableInfo.getName(),
                dataSourceConfigId) != null) {
            throw exception(CODEGEN_TABLE_EXISTS);
        }

        // 构建 CodegenTableDO 对象，插入到 DB 中
        CodegenTableDO table = codegenBuilder.buildTable(tableInfo);
        table.setDataSourceConfigId(dataSourceConfigId);
        table.setScene(CodegenSceneEnum.ADMIN.getScene()); // 默认配置下，使用管理后台的模板
//        table.setAuthor(userApi.getUser(userId).getNickname());
        codegenTableMapper.insert(table);

        // 构建 CodegenColumnDO 数组，插入到 DB 中
        List<CodegenColumnDO> columns = codegenBuilder.buildColumns(table.getId(), tableInfo.getFields());
        // 如果没有主键，则使用第一个字段作为主键
        if (!tableInfo.isHavePrimaryKey()) {
            columns.get(0).setPrimaryKey(true);
        }
        codegenColumnMapper.insertBatch(columns);
        return table.getId();
    }

    private void validateTableInfo(TableInfo tableInfo) {
        if (tableInfo == null) {
            throw exception(CODEGEN_IMPORT_TABLE_NULL);
        }
        if (StrUtil.isEmpty(tableInfo.getComment())) {
            throw exception(CODEGEN_TABLE_INFO_TABLE_COMMENT_IS_NULL);
        }
        if (CollUtil.isEmpty(tableInfo.getFields())) {
            throw exception(CODEGEN_IMPORT_COLUMNS_NULL);
        }
        tableInfo.getFields().forEach(field -> {
            if (StrUtil.isEmpty(field.getComment())) {
                throw exception(CODEGEN_TABLE_INFO_COLUMN_COMMENT_IS_NULL, field.getName());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCodegen(CodegenUpdateReqVO updateReqVO) {
        // 校验是否已经存在
        if (codegenTableMapper.selectById(updateReqVO.getTable().getId()) == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }

        // 更新 table 表定义
        CodegenTableDO updateTableObj = CodegenConvert.INSTANCE.convert(updateReqVO.getTable());
        codegenTableMapper.updateById(updateTableObj);
        // 更新 column 字段定义
        List<CodegenColumnDO> updateColumnObjs = CodegenConvert.INSTANCE.convertList03(updateReqVO.getColumns());
        updateColumnObjs.forEach(updateColumnObj -> codegenColumnMapper.updateById(updateColumnObj));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncCodegenFromDB(String tableId) {
        // 校验是否已经存在
        CodegenTableDO table = codegenTableMapper.selectById(tableId);
        if (table == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }
        // 从数据库中，获得数据库表结构
        TableInfo tableInfo = databaseTableService.getTable(table.getDataSourceConfigId(), table.getTableName());
        // 执行同步
        syncCodegen0(tableId, tableInfo);
    }

    private void syncCodegen0(String tableId, TableInfo tableInfo) {
        // 校验导入的表和字段非空
        validateTableInfo(tableInfo);
        List<TableField> tableFields = tableInfo.getFields();

        // 构建 CodegenColumnDO 数组，只同步新增的字段
        List<CodegenColumnDO> codegenColumns = codegenColumnMapper.selectListByTableId(tableId);
        Set<String> codegenColumnNames = CollectionUtils.convertSet(codegenColumns, CodegenColumnDO::getColumnName);

        //计算需要修改的字段，插入时重新插入，删除时将原来的删除
        BiPredicate<TableField, CodegenColumnDO> pr =
                (tableField, codegenColumn) -> tableField.getMetaInfo().getJdbcType().name().equals(codegenColumn.getDataType())
                        && tableField.getMetaInfo().isNullable() == codegenColumn.getNullable()
                        && tableField.isKeyFlag() == codegenColumn.getPrimaryKey()
                        && tableField.getComment().equals(codegenColumn.getColumnComment());
        Map<String, CodegenColumnDO> codegenColumnDOMap = CollectionUtils.convertMap(codegenColumns, CodegenColumnDO::getColumnName);
        //需要修改的字段
        Set<String> modifyFieldNames = tableFields.stream()
                .filter(tableField -> codegenColumnDOMap.get(tableField.getColumnName()) != null
                        && !pr.test(tableField, codegenColumnDOMap.get(tableField.getColumnName())))
                .map(TableField::getColumnName)
                .collect(Collectors.toSet());
        // 计算需要删除的字段
        Set<String> tableFieldNames = CollectionUtils.convertSet(tableFields, TableField::getName);
        Set<String> deleteColumnIds = codegenColumns.stream()
                .filter(column -> (!tableFieldNames.contains(column.getColumnName())) || modifyFieldNames.contains(column.getColumnName()))
                .map(CodegenColumnDO::getId).collect(Collectors.toSet());
        // 移除已经存在的字段
        tableFields.removeIf(column -> codegenColumnNames.contains(column.getColumnName()) && (!modifyFieldNames.contains(column.getColumnName())));
        if (CollUtil.isEmpty(tableFields) && CollUtil.isEmpty(deleteColumnIds)) {
            throw exception(CODEGEN_SYNC_NONE_CHANGE);
        }

        // 插入新增的字段
        List<CodegenColumnDO> columns = codegenBuilder.buildColumns(tableId, tableFields);
        codegenColumnMapper.insertBatch(columns);
        // 删除不存在的字段
        if (CollUtil.isNotEmpty(deleteColumnIds)) {
            codegenColumnMapper.deleteBatchIds(deleteColumnIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCodegen(String tableId) {
        // 校验是否已经存在
        if (codegenTableMapper.selectById(tableId) == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }

        // 删除 table 表定义
        codegenTableMapper.deleteById(tableId);
        // 删除 column 字段定义
        codegenColumnMapper.deleteListByTableId(tableId);
    }

    @Override
    public PageResult<CodegenTableDO> getCodegenTablePage(CodegenTablePageReqVO pageReqVO) {
        return codegenTableMapper.selectPage(pageReqVO);
    }

    @Override
    public CodegenTableDO getCodegenTablePage(String id) {
        return codegenTableMapper.selectById(id);
    }

    @Override
    public List<CodegenColumnDO> getCodegenColumnListByTableId(String tableId) {
        return codegenColumnMapper.selectListByTableId(tableId);
    }

    @Override
    public Map<String, String> generationCodes(String tableId) {
        // 校验是否已经存在
        CodegenTableDO table = codegenTableMapper.selectById(tableId);
        if (table == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }
        List<CodegenColumnDO> columns = codegenColumnMapper.selectListByTableId(tableId);
        if (CollUtil.isEmpty(columns)) {
            throw exception(CODEGEN_COLUMN_NOT_EXISTS);
        }

        // 执行生成
        return codegenEngine.execute(table, columns);
    }

    @Override
    public List<DatabaseTableRespVO> getDatabaseTableList(String dataSourceConfigId, String name, String comment) {
        List<TableInfo> tables = databaseTableService.getTableList(dataSourceConfigId, name, comment);
        // 移除已经生成的表
        // 移除在 Codegen 中，已经存在的
        Set<String> existsTables = CollectionUtils.convertSet(
                codegenTableMapper.selectListByDataSourceConfigId(dataSourceConfigId), CodegenTableDO::getTableName);
        tables.removeIf(table -> existsTables.contains(table.getName()));
        return CodegenConvert.INSTANCE.convertList04(tables);
    }

}
