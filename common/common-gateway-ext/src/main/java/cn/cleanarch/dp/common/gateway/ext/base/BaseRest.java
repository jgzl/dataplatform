package cn.cleanarch.dp.common.gateway.ext.base;

import cn.cleanarch.dp.common.gateway.ext.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description 父控制器类，提供基础验证方法
 * @Author jianglong
 * @Date 2020/05/16
 * @Version V1.0
 */
public class BaseRest {

    @Autowired
    private Validator validator;

    /**
     * 实体类参数有效性验证
     * @param t
     */
    public <T> void validate(T t) {
        validate(t, false);
    }

    /**
     * 实体类参数有效性验证
     * @param t 验证的实体对象
     * @param isMulti 验证的实体对象
     * @param groups 验证组
     * 验证失败：将错误信息添加到message中
     */
    public <T> void validate(T t, boolean isMulti, Class<?> ...groups) {
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(t, groups);
        if (!constraintViolationSet.isEmpty()  && constraintViolationSet.size() >0) {
            StringBuilder sb = new StringBuilder();
            Iterator<ConstraintViolation<T>> iterator = constraintViolationSet.iterator();
            //true表示多个验证一起组合后抛出
            if (isMulti){
                while (iterator.hasNext()) {
                    ConstraintViolation<T> violation =iterator.next();
                    sb.append(",").append(violation.getMessage());
                }
            }else {
                ConstraintViolation<T> violation =iterator.next();
                sb.append(",").append(violation.getMessage());
            }
            throw new ValidationException(sb.substring(1));
        }
    }

    /***
     * 获取分页索引
     * @param currentPage
     * @return
     */
    public int getCurrentPage(Integer currentPage){
        return currentPage == null?Constants.CURRENT_PAGE:currentPage.intValue();
    }

    /**
     * 获取分页展示行数
     * @param pageSize
     * @return
     */
    public int getPageSize(Integer pageSize){
        return pageSize == null? Constants.PAGE_SIZE:pageSize.intValue();
    }


}
