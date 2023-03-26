package cn.cleanarch.dp.common.data.handler;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.core.constant.SecurityConstants;
import cn.cleanarch.dp.common.oauth.util.AppContextHolder;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author lihaifeng
 * @version 1.0
 * @title: DefaultDBFieldHandler
 * @description: 通用参数填充实现类, 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 * @date 2022/5/30 20:51
 */
@Slf4j
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        LocalDateTime current = LocalDateTime.now();
        String userId = SecurityConstants.DEFAULT_USER_ID;
        if (AppContextHolder.getUser() != null) {
            userId = AppContextHolder.getUser().getUserId();
        }
        this.setFieldValByName("creator", userId,metaObject);
        this.setFieldValByName("createTime",current,metaObject);
        this.setFieldValByName("updater", userId,metaObject);
        this.setFieldValByName("updateTime",current,metaObject);
        this.setFieldValByName("version", CommonConstants.DEFAULT_VERSION,metaObject);
        this.setFieldValByName("deleted",CommonConstants.STATUS_NORMAL,metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        LocalDateTime current = LocalDateTime.now();
        String userId = SecurityConstants.DEFAULT_USER_ID;
        if (AppContextHolder.getUser() != null) {
            userId = AppContextHolder.getUser().getUserId();
        }
        this.setFieldValByName("updater", userId,metaObject);
        this.setFieldValByName("updateTime",current,metaObject);
    }
}
