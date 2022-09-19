package cn.cleanarch.gw.common.data.handler;

import cn.cleanarch.gw.common.core.constant.CommonConstants;
import cn.cleanarch.gw.common.core.constant.SecurityConstants;
import cn.cleanarch.gw.common.model.base.BaseDO;
import cn.cleanarch.gw.common.security.utils.AppContextHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * @author lihaifeng
 * @version 1.0
 * @title: DefaultDBFieldHandler
 * @description: 通用参数填充实现类, 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 * @date: 2022/5/30 20:51
 */
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO baseDO) {

            LocalDateTime current = LocalDateTime.now();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(current);
            }

            Long userId = SecurityConstants.DEFAULT_USER_ID;
            if (AppContextHolder.getUser() != null) {
                userId = AppContextHolder.getUser().getUserId();
            }
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreator())) {
                baseDO.setCreator(userId.toString());
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
                baseDO.setUpdater(userId.toString());
            }
//            baseDO.setVersion(CommonConstants.DEFAULT_VERSION);
//            baseDO.setDeleted(CommonConstants.STATUS_NORMAL);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO baseDO) {
            LocalDateTime current = LocalDateTime.now();
            baseDO.setUpdateTime(current);
            Long userId = SecurityConstants.DEFAULT_USER_ID;
            if (AppContextHolder.getUser() != null) {
                userId = AppContextHolder.getUser().getUserId();
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getUpdater())) {
                baseDO.setUpdater(userId.toString());
            }
        }
    }
}
