package cn.cleanarch.dp.infra.convert.codegen;

import cn.cleanarch.dp.infra.dataobject.codegen.CodegenColumnDO;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenTableDO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenDetailRespVO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenPreviewRespVO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenUpdateReqVO;
import cn.cleanarch.dp.infra.vo.codegen.column.CodegenColumnRespVO;
import cn.cleanarch.dp.infra.vo.codegen.table.CodegenTableRespVO;
import cn.cleanarch.dp.infra.vo.codegen.table.DatabaseTableRespVO;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface CodegenConvert {

    CodegenConvert INSTANCE = Mappers.getMapper(CodegenConvert.class);

    // ========== TableInfo 相关 ==========

    @Mappings({
            @Mapping(source = "name", target = "tableName"),
            @Mapping(source = "comment", target = "tableComment"),
    })
    CodegenTableDO convert(TableInfo bean);

    List<CodegenColumnDO> convertList(List<TableField> list);

    @Mappings({
            @Mapping(source = "name", target = "columnName"),
            @Mapping(source = "type", target = "dataType"),
            @Mapping(source = "comment", target = "columnComment"),
            @Mapping(source = "metaInfo.nullable", target = "nullable"),
            @Mapping(source = "keyFlag", target = "primaryKey"),
            @Mapping(source = "keyIdentityFlag", target = "autoIncrement"),
            @Mapping(source = "columnType.type", target = "javaType"),
            @Mapping(source = "propertyName", target = "javaField"),
    })
    CodegenColumnDO convert(TableField bean);

    // ========== CodegenTableDO 相关 ==========

//    List<CodegenTableRespVO> convertList02(List<CodegenTableDO> list);

    CodegenTableRespVO convert(CodegenTableDO bean);

    // ========== CodegenTableDO 相关 ==========

    List<CodegenColumnRespVO> convertList02(List<CodegenColumnDO> list);

    CodegenTableDO convert(CodegenUpdateReqVO.Table bean);

    List<CodegenColumnDO> convertList03(List<CodegenUpdateReqVO.Column> columns);

    List<DatabaseTableRespVO> convertList04(List<TableInfo> list);

    // ========== 其它 ==========

    default CodegenDetailRespVO convert(CodegenTableDO table, List<CodegenColumnDO> columns) {
        CodegenDetailRespVO respVO = new CodegenDetailRespVO();
        respVO.setTable(convert(table));
        respVO.setColumns(convertList02(columns));
        return respVO;
    }

    default List<CodegenPreviewRespVO> convert(Map<String, String> codes) {
        return codes.entrySet().stream().map(entry -> {
            CodegenPreviewRespVO respVO = new CodegenPreviewRespVO();
            respVO.setFilePath(entry.getKey());
            respVO.setCode(entry.getValue());
            return respVO;
        }).collect(Collectors.toList());
    }

}
