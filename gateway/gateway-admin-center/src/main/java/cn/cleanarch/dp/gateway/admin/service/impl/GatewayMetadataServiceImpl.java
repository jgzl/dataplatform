package cn.cleanarch.dp.gateway.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import cn.cleanarch.dp.gateway.admin.dataobject.GatewayMetadataDO;
import cn.cleanarch.dp.gateway.admin.mapper.GatewayMetadataMapper;
import cn.cleanarch.dp.gateway.admin.service.GatewayMetadataService;
 /**
 * 网关管理-元数据管理;(GW_GATEWAY_METADATA)表服务实现类
 * @author li7hai26@gmail.com
 * @date 2022-9-20
 */
@Service
public class GatewayMetadataServiceImpl extends ServiceImpl<GatewayMetadataMapper, GatewayMetadataDO> implements GatewayMetadataService{
    @Autowired
    private GatewayMetadataMapper gatewayMetadataMapper;
    
    /**
     * 分页查询
     *
     * @param gatewayMetadata 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<GatewayMetadataDO> paginQuery(GatewayMetadataDO gatewayMetadata, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<GatewayMetadataDO> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(gatewayMetadata.getApplicationCode())){
            queryWrapper.eq(GatewayMetadataDO::getApplicationCode, gatewayMetadata.getApplicationCode());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getMethod())){
            queryWrapper.eq(GatewayMetadataDO::getMethod, gatewayMetadata.getMethod());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getPath())){
            queryWrapper.eq(GatewayMetadataDO::getPath, gatewayMetadata.getPath());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getDescription())){
            queryWrapper.eq(GatewayMetadataDO::getDescription, gatewayMetadata.getDescription());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getRpcType())){
            queryWrapper.eq(GatewayMetadataDO::getRpcType, gatewayMetadata.getRpcType());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getRpcExtra())){
            queryWrapper.eq(GatewayMetadataDO::getRpcExtra, gatewayMetadata.getRpcExtra());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getTag())){
            queryWrapper.eq(GatewayMetadataDO::getTag, gatewayMetadata.getTag());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getCreator())){
            queryWrapper.eq(GatewayMetadataDO::getCreator, gatewayMetadata.getCreator());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getUpdater())){
            queryWrapper.eq(GatewayMetadataDO::getUpdater, gatewayMetadata.getUpdater());
        }
        if(StrUtil.isNotBlank(gatewayMetadata.getDeleted())){
            queryWrapper.eq(GatewayMetadataDO::getDeleted, gatewayMetadata.getDeleted());
        }
        queryWrapper.eq(GatewayMetadataDO::getDeleted, cn.cleanarch.dp.common.core.constant.CommonConstants.STATUS_NORMAL);
        //2. 执行分页查询
        Page<GatewayMetadataDO> pagin = new Page<>(current , size , true);
        IPage<GatewayMetadataDO> selectResult = gatewayMetadataMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
}