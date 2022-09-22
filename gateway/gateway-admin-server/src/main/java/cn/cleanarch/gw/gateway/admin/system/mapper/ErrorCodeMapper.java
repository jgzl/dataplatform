package cn.cleanarch.gw.gateway.admin.system.mapper;

import cn.cleanarch.gw.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.gw.common.data.query.QueryWrapperX;
import cn.cleanarch.gw.gateway.admin.system.domain.SysErrorCodeDO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysErrorCodeExportReqVO;
import cn.cleanarch.gw.gateway.admin.system.vo.SysErrorCodePageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface ErrorCodeMapper extends ExtendBaseMapper<SysErrorCodeDO> {

    default IPage<SysErrorCodeDO> selectPage(SysErrorCodePageReqVO reqVO) {
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
