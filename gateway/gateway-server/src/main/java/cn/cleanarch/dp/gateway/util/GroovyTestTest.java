//package cn.cleanarch.dp.gateway.util;
//
//import lombok.SneakyThrows;
//
//public class GroovyTestTest {
//    @SneakyThrows
//    public static void main(String[] args) {
//        String script = "package cn.cleanarch.dp.gateway.util\n" +
//                "\n" +
//                "public class GroovyTest implements cn.cleanarch.dp.gateway.util.IGroovyTest {\n" +
//                "    public String info() {\n" +
//                "        return \"success\";\n" +
//                "    }\n" +
//                "}";
//        IGroovyTest groovyTest = (IGroovyTest)GroovyScriptUtils.newObjectInstance(script);
//        String info = groovyTest.info();
//        System.out.println("info:"+info);
//    }
//}
