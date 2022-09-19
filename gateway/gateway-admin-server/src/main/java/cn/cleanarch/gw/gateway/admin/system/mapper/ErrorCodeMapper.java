package cn.cleanarch.gw.gateway.admin.system.mapper;

import cn.cleanarch.gw.common.data.mapper.ExtendBaseMapper;
import cn.cleanarch.gw.common.data.query.QueryWrapperX;
import cn.cleanarch.gw.common.model.system.domain.ErrorCodeDO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodeExportReqVO;
import cn.cleanarch.gw.common.model.system.vo.ErrorCodePageReqVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface ErrorCodeMapper extends ExtendBaseMapper<ErrorCodeDO> {

    default IPage<ErrorCodeDO> selectPage(ErrorCodePageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<ErrorCodeDO>()
                .eqIfPresent("type", reqVO.getType())
                .likeIfPresent("application_name", reqVO.getApplicationName())
                .eqIfPresent("code", reqVO.getCode())
                .likeIfPresent("message", reqVO.getMessage())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByAsc("application_name", "code"));
    }

    default List<ErrorCodeDO> selectList(ErrorCodeExportReqVO reqVO) {
        return selectList(new QueryWrapperX<ErrorCodeDO>()
                .eqIfPresent("type", reqVO.getType())
                .likeIfPresent("application_name", reqVO.getApplicationName())
                .eqIfPresent("code", reqVO.getCode())
                .likeIfPresent("message", reqVO.getMessage())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByAsc("application_name", "code"));
    }

    default List<ErrorCodeDO> selectListByCodes(Collection<Integer> codes) {
        return selectList(new QueryWrapper<ErrorCodeDO>().in("code", codes));
    }

    default ErrorCodeDO selectByCode(Integer code) {
        return selectOne(new QueryWrapper<ErrorCodeDO>().eq("code", code));
    }

    default List<ErrorCodeDO> selectListByApplicationNameAndUpdateTimeGt(String applicationName, LocalDateTime minUpdateTime) {
        return selectList(new QueryWrapperX<ErrorCodeDO>().eq("application_name", applicationName)
                .gtIfPresent("update_time", minUpdateTime));
    }

}
