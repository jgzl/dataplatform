package cn.cleanarch.dp.tool.mapper;

import cn.cleanarch.dp.metadata.entity.MetadataColumnEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 元数据信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-07-29
 */
@Mapper
public interface MetadataColumnMapper extends BaseMapper<MetadataColumnEntity> {

    <E extends IPage<MetadataColumnEntity>> E selectPageWithAuth(E page, @Param(Constants.WRAPPER) Wrapper<MetadataColumnEntity> queryWrapper, @Param("roles") List<String> roles);
}