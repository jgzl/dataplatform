package cn.cleanarch.dp.infra.controller.file;

import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.common.core.utils.WebmvcUtil;
import cn.cleanarch.dp.infra.convert.file.FileConvert;
import cn.cleanarch.dp.infra.dataobject.file.FileDO;
import cn.cleanarch.dp.infra.service.file.FileService;
import cn.cleanarch.dp.infra.vo.file.file.FilePageReqVO;
import cn.cleanarch.dp.infra.vo.file.file.FileRespVO;
import cn.cleanarch.dp.infra.vo.file.file.FileUploadReqVO;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static cn.cleanarch.dp.common.core.model.R.success;

@Tag(name = "管理后台 - 文件存储")
@RestController
@RequestMapping("/infra/file")
@Validated
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "上传文件")
    public R<String> uploadFile(FileUploadReqVO uploadReqVO) throws Exception {
        MultipartFile file = uploadReqVO.getFile();
        String path = uploadReqVO.getPath();
        return success(fileService.createFile(file.getOriginalFilename(), path, IoUtil.readBytes(file.getInputStream())));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除文件")
    @Parameter(name = "id", description = "编号", required = true)
    ////@PreAuthorize("@pms.hasPermission('infra:file:delete')")
    public R<Boolean> deleteFile(@RequestParam("id") String id) throws Exception {
        fileService.deleteFile(id);
        return success(true);
    }

    @GetMapping("/{configId}/get/**")
    @PermitAll
    @Operation(summary = "下载文件")
    @Parameter(name = "configId", description = "配置编号",  required = true)
    public void getFileContent(HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable("configId") String configId) throws Exception {
        // 获取请求的路径
        String path = StrUtil.subAfter(request.getRequestURI(), "/get/", false);
        if (StrUtil.isEmpty(path)) {
            throw new IllegalArgumentException("结尾的 path 路径必须传递");
        }

        // 读取内容
        byte[] content = fileService.getFileContent(configId, path);
        if (content == null) {
            log.warn("[getFileContent][configId({}) path({}) 文件不存在]", configId, path);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        WebmvcUtil.writeAttachment(response, path, content);
    }

    @GetMapping("/page")
    @Operation(summary = "获得文件分页")
    ////@PreAuthorize("@pms.hasPermission('infra:file:query')")
    public R<PageResult<FileRespVO>> getFilePage(@Valid FilePageReqVO pageVO) {
        PageResult<FileDO> pageResult = fileService.getFilePage(pageVO);
        return success(FileConvert.INSTANCE.convertPage(pageResult));
    }

}
