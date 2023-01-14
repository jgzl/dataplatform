package cn.cleanarch.dp.tool.mapper;

import cn.cleanarch.dp.tool.metadata.entity.MetadataChangeRecordEntity;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 * 元数据变更记录表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-07-30
 */
@Mapper
public interface MetadataChangeRecordMapper extends BaseMapper<MetadataChangeRecordEntity> {

    @Override
    MetadataChangeRecordEntity selectById(Serializable id);

    @Override
    <E extends IPage<MetadataChangeRecordEntity>> E selectPage(E page, @Param(Constants.WRAPPER) Wrapper<MetadataChangeRecordEntity> queryWrapper);
}
