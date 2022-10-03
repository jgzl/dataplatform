package cn.cleanarch.dp.tool.service;

import cn.cleanarch.dp.metadata.dto.MetadataAuthorizeDto;
import cn.cleanarch.dp.metadata.entity.MetadataAuthorizeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 数据授权信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-10-23
 */
public interface MetadataAuthorizeService extends IService<MetadataAuthorizeEntity> {

    List<String> getAuthorizedMetadata(String id);

    void metadataAuthorize(MetadataAuthorizeDto metadataAuthorizeDto);

    void refreshCache();
}
