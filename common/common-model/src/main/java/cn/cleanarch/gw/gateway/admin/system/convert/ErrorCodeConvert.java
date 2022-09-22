package cn.cleanarch.gw.gateway.admin.system.convert;

import cn.cleanarch.gw.gateway.admin.system.domain.SysErrorCodeDO;
import cn.cleanarch.gw.gateway.admin.system.dto.SysErrorCodeAutoGenerateReqDTO;
import cn.cleanarch.gw.gateway.admin.system.dto.SysErrorCodeRespDTO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysErrorCodeCreateReqVO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysErrorCodeRespVO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysErrorCodeUpdateReqVO;
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
public interface ErrorCodeConvert {

    ErrorCodeConvert INSTANCE = Mappers.getMapper(ErrorCodeConvert.class);

    SysErrorCodeDO convert(SysErrorCodeCreateReqVO bean);

    SysErrorCodeDO convert(SysErrorCodeUpdateReqVO bean);

    SysErrorCodeRespVO convert(SysErrorCodeDO bean);

    List<SysErrorCodeRespVO> convertList(List<SysErrorCodeDO> list);

    Page<SysErrorCodeRespVO> convertPage(IPage<SysErrorCodeDO> page);

    SysErrorCodeDO convert(SysErrorCodeAutoGenerateReqDTO bean);

    List<SysErrorCodeRespDTO> convertList03(List<SysErrorCodeDO> list);

}
