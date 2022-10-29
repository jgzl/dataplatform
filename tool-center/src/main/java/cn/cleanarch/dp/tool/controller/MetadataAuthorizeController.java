package cn.cleanarch.dp.tool.controller;

import cn.cleanarch.dp.common.core.model.R;
import cn.cleanarch.dp.metadata.dto.MetadataAuthorizeDto;
import cn.cleanarch.dp.tool.convert.MetadataAuthorizeConvert;
import cn.cleanarch.dp.tool.service.MetadataAuthorizeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 数据授权信息表 前端控制器
 * </p>
 *
 * @author yuwei
 * @since 2020-10-23
 */
@Api(tags = {"元数据授权信息表"})
@RestController
@RequestMapping("/authorizes")
public class MetadataAuthorizeController {

    @Autowired
    private MetadataAuthorizeService metadataAuthorizeService;

    @Autowired
    private MetadataAuthorizeConvert metadataAuthorizeMapper;

    @GetMapping("/getAuthorizedMetadata/{id}")
    public R getAuthorizedMetadata(@PathVariable String id) {
        List<String> list = metadataAuthorizeService.getAuthorizedMetadata(id);
        return R.success(list);
    }

    @PostMapping("/metadata")
    public R metadataAuthorize(@RequestBody @Validated MetadataAuthorizeDto metadataAuthorizeDto) {
        metadataAuthorizeService.metadataAuthorize(metadataAuthorizeDto);
        return R.success();
    }

    /**
     * 刷新缓存
     *
     * @return
     */
    @GetMapping("/refresh")
    public R refreshCache() {
        metadataAuthorizeService.refreshCache();
        return R.success();
    }
}
