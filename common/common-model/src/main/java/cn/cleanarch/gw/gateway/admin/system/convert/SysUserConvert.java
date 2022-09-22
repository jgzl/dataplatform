package cn.cleanarch.gw.gateway.admin.system.convert;

import cn.cleanarch.gw.common.BaseConvert;
import cn.cleanarch.gw.gateway.admin.system.domain.SysUserDO;
import cn.cleanarch.gw.gateway.admin.system.dto.SysUserInfoDTO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysUserVO;
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
