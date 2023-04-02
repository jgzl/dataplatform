package cn.cleanarch.dp.infra.convert.file;

import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigRespVO;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigUpdateReqVO;
import cn.cleanarch.dp.infra.dataobject.file.FileConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件配置 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigCreateReqVO bean);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigUpdateReqVO bean);

    FileConfigRespVO convert(FileConfigDO bean);

    List<FileConfigRespVO> convertList(List<FileConfigDO> list);

    PageResult<FileConfigRespVO> convertPage(PageResult<FileConfigDO> page);

}
