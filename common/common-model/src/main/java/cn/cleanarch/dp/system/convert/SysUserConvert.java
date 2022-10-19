package cn.cleanarch.dp.system.convert;

import cn.cleanarch.dp.common.model.BaseConvert;
import cn.cleanarch.dp.system.dataobject.user.SysUserDO;
import cn.cleanarch.dp.system.dto.SysUserInfoDTO;
import cn.cleanarch.dp.system.vo.user.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author li7hai26@gmail.com
 * @date 2021/12/24
 */
@Mapper
public interface SysUserConvert extends BaseConvert<SysUserVO, SysUserDO> {
    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);
    SysUserInfoDTO convert(SysUserDO sysUserDO);
}
