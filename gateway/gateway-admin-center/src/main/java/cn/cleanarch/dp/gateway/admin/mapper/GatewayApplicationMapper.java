package cn.cleanarch.dp.gateway.admin.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayApplicationDO;
import org.apache.ibatis.annotations.Param;

 /**
 * 网关管理-应用服务表;(GW_GATEWAY_APPLICATION)表数据库访问层
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
public interface GatewayApplicationMapper  extends BaseMapper<GatewayApplicationDO>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<GatewayApplicationDO> selectByPage(IPage<GatewayApplicationDO> page , @Param(Constants.WRAPPER) Wrapper<GatewayApplicationDO> wrapper);
}