package cn.cleanarch.dp.system.mapper.user;

import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.system.dataobject.user.SysUserDO;
import cn.cleanarch.dp.system.dto.SysUserDTO;
import cn.cleanarch.dp.system.vo.user.SysUserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2017-10-29
 */
public interface SysUserMapper extends ExtendBaseMapper<SysUserDO> {

    /**
     * 通过用户名查询用户信息（含有角色信息）
     *
     * @param username 用户名
     * @return sysUserVo
     */
    SysUserVO getUserVoByUsername(String username);

    /**
     * 分页查询用户信息（含角色）
     *
     * @param page    分页
     * @param userDTO 查询参数
     * @return list
     */
    IPage<SysUserVO> getUserVosPage(Page page, @Param("query") SysUserDTO userDTO);

    /**
     * 查询用户信息（含角色）
     *
     * @param userDTO 查询参数
     * @return list
     */
    List<SysUserVO> getUserVosPage(@Param("query") SysUserDTO userDTO);

    /**
     * 通过ID查询用户信息
     *
     * @param id 用户ID
     * @return sysUserVo
     */
    SysUserVO getUserVoById(String id);

}
