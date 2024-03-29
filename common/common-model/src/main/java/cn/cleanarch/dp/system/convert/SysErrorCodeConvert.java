package cn.cleanarch.dp.system.convert;

import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.system.dataobject.errorcode.SysErrorCodeDO;
import cn.cleanarch.dp.system.dto.SysErrorCodeAutoGenerateReqDTO;
import cn.cleanarch.dp.system.dto.SysErrorCodeRespDTO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeCreateReqVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeRespVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeUpdateReqVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 错误码 Convert
 *
 * @author lihaifeng
 */
@Mapper
public interface SysErrorCodeConvert {

    SysErrorCodeConvert INSTANCE = Mappers.getMapper(SysErrorCodeConvert.class);

    SysErrorCodeDO convert(SysErrorCodeCreateReqVO bean);

    SysErrorCodeDO convert(SysErrorCodeUpdateReqVO bean);

    SysErrorCodeRespVO convert(SysErrorCodeDO bean);

    List<SysErrorCodeRespVO> convertList(List<SysErrorCodeDO> list);

    PageResult<SysErrorCodeRespVO> convertPage(PageResult<SysErrorCodeDO> page);

    SysErrorCodeDO convert(SysErrorCodeAutoGenerateReqDTO bean);

    List<SysErrorCodeRespDTO> convertList03(List<SysErrorCodeDO> list);

}
