package cn.cleanarch.dp.tool.service;

import cn.cleanarch.dp.metadata.dto.MetadataColumnDto;
import cn.cleanarch.dp.metadata.entity.MetadataColumnEntity;
import cn.cleanarch.dp.metadata.query.MetadataColumnQuery;
import cn.cleanarch.dp.metadata.vo.MetadataTreeVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 元数据信息表 服务类
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
public interface MetadataColumnService extends IService<MetadataColumnEntity> {

    MetadataColumnEntity saveMetadataColumn(MetadataColumnDto metadataColumn);

    MetadataColumnEntity updateMetadataColumn(MetadataColumnDto metadataColumn);

    MetadataColumnEntity getMetadataColumnById(String id);

    void deleteMetadataColumnById(String id);

    void deleteMetadataColumnBatch(List<String> ids);

    List<MetadataTreeVo> getDataMetadataTree(String level, MetadataColumnQuery metadataColumnQuery);

    List<MetadataColumnEntity> getDataMetadataColumnList(MetadataColumnQuery metadataColumnQuery);

    <E extends IPage<MetadataColumnEntity>> E pageWithAuth(E page, Wrapper<MetadataColumnEntity> queryWrapper);
}
