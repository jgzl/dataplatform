package cn.cleanarch.dp.infra.controller.codegen;

import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import cn.cleanarch.dp.common.data.util.PageCovertUtil;
import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import cn.cleanarch.dp.infra.service.codegen.CodegenService;
import cn.cleanarch.dp.infra.convert.codegen.CodegenConvert;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenColumnDO;
import cn.cleanarch.dp.infra.dataobject.codegen.CodegenTableDO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenCreateListReqVO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenDetailRespVO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenPreviewRespVO;
import cn.cleanarch.dp.infra.vo.codegen.CodegenUpdateReqVO;
import cn.cleanarch.dp.infra.vo.codegen.table.CodegenTablePageReqVO;
import cn.cleanarch.dp.infra.vo.codegen.table.CodegenTableRespVO;
import cn.cleanarch.dp.infra.vo.codegen.table.DatabaseTableRespVO;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Api(tags = "管理后台 - 代码生成器")
@RestController
@RequestMapping("/infra/codegen")
@Validated
public class CodegenController {

    @Resource
    private CodegenService codegenService;

    @GetMapping("/db/table/list")
    @ApiOperation(value = "获得数据库自带的表定义列表", notes = "会过滤掉已经导入 Codegen 的表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dataSourceConfigId", value = "数据源配置的编号", required = true, example = "1", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "name", value = "表名，模糊匹配", example = "yudao", dataTypeClass = String.class),
            @ApiImplicitParam(name = "comment", value = "描述，模糊匹配", example = "芋道", dataTypeClass = String.class)
    })
    //@PreAuthorize("@pms.hasPermission('infra:codegen:query')")
    public R<List<DatabaseTableRespVO>> getDatabaseTableList(
            @RequestParam(value = "dataSourceConfigId") String dataSourceConfigId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "comment", required = false) String comment) {
        return R.success(codegenService.getDatabaseTableList(dataSourceConfigId, name, comment));
    }

    @GetMapping("/table/page")
    @ApiOperation("获得表定义分页")
    //@PreAuthorize("@pms.hasPermission('infra:codegen:query')")
    public R<PageResult<CodegenTableRespVO>> getCodeGenTablePage(@Valid CodegenTablePageReqVO pageReqVO) {
        PageResult<CodegenTableDO> pageResult = codegenService.getCodegenTablePage(pageReqVO);
        return R.success(PageCovertUtil.pageVoCovert(pageResult,CodegenTableRespVO.class));
    }

    @GetMapping("/detail")
    @ApiOperation("获得表和字段的明细")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    //@PreAuthorize("@pms.hasPermission('infra:codegen:query')")
    public R<CodegenDetailRespVO> getCodegenDetail(@RequestParam("tableId") String tableId) {
        CodegenTableDO table = codegenService.getCodegenTablePage(tableId);
        List<CodegenColumnDO> columns = codegenService.getCodegenColumnListByTableId(tableId);
        // 拼装返回
        return R.success(CodegenConvert.INSTANCE.convert(table, columns));
    }

    @ApiOperation("基于数据库的表结构，创建代码生成器的表和字段定义")
    @PostMapping("/create-list")
    //@PreAuthorize("@pms.hasPermission('infra:codegen:create')")
    public R<List<String>> createCodegenList(@Valid @RequestBody CodegenCreateListReqVO reqVO) {
        return R.success(codegenService.createCodegenList(AppContextHolder.getUser().getUserId(), reqVO));
    }

    @ApiOperation("更新数据库的表和字段定义")
    @PutMapping("/update")
    //@PreAuthorize("@pms.hasPermission('infra:codegen:update')")
    public R<Boolean> updateCodegen(@Valid @RequestBody CodegenUpdateReqVO updateReqVO) {
        codegenService.updateCodegen(updateReqVO);
        return R.success(true);
    }

    @ApiOperation("基于数据库的表结构，同步数据库的表和字段定义")
    @PutMapping("/sync-from-db")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    //@PreAuthorize("@pms.hasPermission('infra:codegen:update')")
    public R<Boolean> syncCodegenFromDB(@RequestParam("tableId") String tableId) {
        codegenService.syncCodegenFromDB(tableId);
        return R.success(true);
    }

    @ApiOperation("删除数据库的表和字段定义")
    @DeleteMapping("/delete")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    //@PreAuthorize("@pms.hasPermission('infra:codegen:delete')")
    public R<Boolean> deleteCodegen(@RequestParam("tableId") String tableId) {
        codegenService.deleteCodegen(tableId);
        return R.success(true);
    }

    @ApiOperation("预览生成代码")
    @GetMapping("/preview")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    //@PreAuthorize("@pms.hasPermission('infra:codegen:preview')")
    public R<List<CodegenPreviewRespVO>> previewCodegen(@RequestParam("tableId") String tableId) {
        Map<String, String> codes = codegenService.generationCodes(tableId);
        return R.success(CodegenConvert.INSTANCE.convert(codes));
    }

    @ApiOperation("下载生成代码")
    @GetMapping("/download")
    @ApiImplicitParam(name = "tableId", value = "表编号", required = true, example = "1024", dataTypeClass = Long.class)
    //@PreAuthorize("@pms.hasPermission('infra:codegen:download')")
    public void downloadCodegen(@RequestParam("tableId") String tableId,
                                HttpServletResponse response) throws IOException {
        // 生成代码
        Map<String, String> codes = codegenService.generationCodes(tableId);
        // 构建 zip 包
        String[] paths = codes.keySet().toArray(new String[0]);
        ByteArrayInputStream[] ins = codes.values().stream().map(IoUtil::toUtf8Stream).toArray(ByteArrayInputStream[]::new);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipUtil.zip(outputStream, paths, ins);
        // 输出
        WebmvcUtil.writeAttachment(response, "codegen.zip", outputStream.toByteArray());
    }

}
