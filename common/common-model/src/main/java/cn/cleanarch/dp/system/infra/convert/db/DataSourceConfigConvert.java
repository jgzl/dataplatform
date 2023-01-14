package cn.cleanarch.dp.system.infra.convert.db;

import cn.cleanarch.dp.system.infra.dataobject.db.DataSourceConfigDO;
import cn.cleanarch.dp.system.infra.vo.db.DataSourceConfigCreateReqVO;
import cn.cleanarch.dp.system.infra.vo.db.DataSourceConfigRespVO;
import cn.cleanarch.dp.system.infra.vo.db.DataSourceConfigUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 数据源配置 Convert
 *
 * @author li7hai26@gmail.com
 */
@Mapper
public interface DataSourceConfigConvert {

    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);

    DataSourceConfigDO convert(DataSourceConfigCreateReqVO bean);

    DataSourceConfigDO convert(DataSourceConfigUpdateReqVO bean);

    DataSourceConfigRespVO convert(DataSourceConfigDO bean);

    List<DataSourceConfigRespVO> convertList(List<DataSourceConfigDO> list);

}
