package cn.cleanarch.dp.gateway.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayApplicationDO;
import cn.cleanarch.dp.gateway.admin.mapper.GatewayApplicationMapper;
import cn.cleanarch.dp.gateway.admin.service.GatewayApplicationService;
 /**
 * 网关管理-应用服务表;(GW_GATEWAY_APPLICATION)表服务实现类
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
@Service
public class GatewayApplicationServiceImpl extends ServiceImpl<GatewayApplicationMapper, GatewayApplicationDO> implements GatewayApplicationService{
    @Autowired
    private GatewayApplicationMapper gatewayApplicationMapper;
    
    /**
     * 分页查询
     *
     * @param gatewayApplication 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<GatewayApplicationDO> paginQuery(GatewayApplicationDO gatewayApplication, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<GatewayApplicationDO> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(gatewayApplication.getApplicationCode())){
            queryWrapper.eq(GatewayApplicationDO::getApplicationCode, gatewayApplication.getApplicationCode());
        }
        if(StrUtil.isNotBlank(gatewayApplication.getApplicationName())){
            queryWrapper.eq(GatewayApplicationDO::getApplicationName, gatewayApplication.getApplicationName());
        }
        if(StrUtil.isNotBlank(gatewayApplication.getDeploymentMode())){
            queryWrapper.eq(GatewayApplicationDO::getDeploymentMode, gatewayApplication.getDeploymentMode());
        }
        if(StrUtil.isNotBlank(gatewayApplication.getCreator())){
            queryWrapper.eq(GatewayApplicationDO::getCreator, gatewayApplication.getCreator());
        }
        if(StrUtil.isNotBlank(gatewayApplication.getUpdater())){
            queryWrapper.eq(GatewayApplicationDO::getUpdater, gatewayApplication.getUpdater());
        }
        if(StrUtil.isNotBlank(gatewayApplication.getDeleted())){
            queryWrapper.eq(GatewayApplicationDO::getDeleted, gatewayApplication.getDeleted());
        }
        queryWrapper.eq(GatewayApplicationDO::getDeleted, cn.cleanarch.dp.common.core.constant.CommonConstants.STATUS_NORMAL);
        //2. 执行分页查询
        Page<GatewayApplicationDO> pagin = new Page<>(current , size , true);
        IPage<GatewayApplicationDO> selectResult = gatewayApplicationMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
}