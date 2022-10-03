package cn.cleanarch.dp.tool.mapper;


import cn.cleanarch.dp.metadata.entity.MetadataAuthorizeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 数据授权信息表 Mapper 接口
 * </p>
 *
 * @author yuwei
 * @since 2020-10-23
 */
@Mapper
public interface MetadataAuthorizeDao extends BaseMapper<MetadataAuthorizeEntity> {

}
