package cn.cleanarch.dp.common.data.mybatis;

import cn.cleanarch.dp.common.core.constant.CommonConstants;
import cn.cleanarch.dp.common.data.handler.DefaultDBFieldHandler;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author li7hai26@gmail.com
 * @date 2020-02-08
 */
@Configuration
@MapperScan(value = "${" + CommonConstants.CONFIGURATION_PREFIX + ".mybatis.base-package}", annotationClass = Mapper.class,
        lazyInitialization = "${" + CommonConstants.CONFIGURATION_PREFIX + ".mybatis.lazy-initialization:false}")
public class MybatisPlusConfiguration {

    /**
     * mybatis plus 拦截器配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    @Bean
    public MetaObjectHandler defaultMetaObjectHandler(){
        return new DefaultDBFieldHandler(); // 自动填充参数类
    }

}
