package cn.cleanarch.dp.gateway.util;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilationFailedException;

import java.lang.reflect.InvocationTargetException;

public class GroovyScriptUtils {

    private GroovyScriptUtils(){
    }

    public static Class newGroovyInstance(String script) throws  IllegalArgumentException, SecurityException {
        // 每次执行都需要通过groovy动态反射加载类，高并发下有性能问题
        return new GroovyClassLoader().parseClass(script);
    }

    public static Object newObjectInstance(String script) throws CompilationFailedException, InstantiationException, IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class clazz = newGroovyInstance(script);
        return clazz.getDeclaredConstructor().newInstance();
    }

}