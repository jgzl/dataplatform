package cn.cleanarch.dp.infra.mapper.file;

import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.infra.dataobject.file.FileContentDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileContentMapper extends ExtendBaseMapper<FileContentDO> {
}
