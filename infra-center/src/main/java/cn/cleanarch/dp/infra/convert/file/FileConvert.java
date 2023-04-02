package cn.cleanarch.dp.infra.convert.file;

import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.infra.vo.file.file.FileRespVO;
import cn.cleanarch.dp.infra.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
