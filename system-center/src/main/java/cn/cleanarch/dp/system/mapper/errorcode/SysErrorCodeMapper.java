package cn.cleanarch.dp.system.mapper.errorcode;

import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.dp.common.data.query.QueryWrapperX;
import cn.cleanarch.dp.system.dataobject.errorcode.SysErrorCodeDO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodeExportReqVO;
import cn.cleanarch.dp.system.vo.errorcode.SysErrorCodePageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 错误管理 Mapper 接口
 * </p>
 *
 * @author li7hai26@gmail.com
 * @since 2018-01-20
 */
public interface SysErrorCodeMapper extends ExtendBaseMapper<SysErrorCodeDO> {

    default PageResult<SysErrorCodeDO> selectPage(SysErrorCodePageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<SysErrorCodeDO>()
                .eqIfPresent("type", reqVO.getType())
                .likeIfPresent("application_name", reqVO.getApplicationName())
                .eqIfPresent("code", reqVO.getCode())
                .likeIfPresent("message", reqVO.getMessage())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByAsc("application_name", "code"));
    }

    default List<SysErrorCodeDO> selectList(SysErrorCodeExportReqVO reqVO) {
        return selectList(new QueryWrapperX<SysErrorCodeDO>()
                .eqIfPresent("type", reqVO.getType())
                .likeIfPresent("application_name", reqVO.getApplicationName())
                .eqIfPresent("code", reqVO.getCode())
                .likeIfPresent("message", reqVO.getMessage())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByAsc("application_name", "code"));
    }

    default List<SysErrorCodeDO> selectListByCodes(Collection<Integer> codes) {
        return selectList(new QueryWrapper<SysErrorCodeDO>().in("code", codes));
    }

    default SysErrorCodeDO selectByCode(Integer code) {
        return selectOne(new QueryWrapper<SysErrorCodeDO>().eq("code", code));
    }

    default List<SysErrorCodeDO> selectListByApplicationNameAndUpdateTimeGt(String applicationName, LocalDateTime minUpdateTime) {
        return selectList(new QueryWrapperX<SysErrorCodeDO>().eq("application_name", applicationName)
                .gtIfPresent("update_time", minUpdateTime));
    }

}
