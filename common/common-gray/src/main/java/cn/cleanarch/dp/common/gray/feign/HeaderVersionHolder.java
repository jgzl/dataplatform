package cn.cleanarch.dp.common.gray.feign;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 解决线程之间version的传递
 * @author 李海峰
 * @date 2022年06月23日 17:06
 */
public class HeaderVersionHolder {

    public static ThreadLocal<String> VERSION = new TransmittableThreadLocal<String>();

    public static void setVersion(String version){
        VERSION.set(version);
    }

    public static String getVersion(){
        return VERSION.get();
    }

    public static void remove(){
        VERSION.remove();
    }


}