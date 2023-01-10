package cn.cleanarch.dp.common.core.utils.poi;

/**
 * Excel数据格式处理适配器
 *
 * @author li7hai26@gmail.com
 */
public interface ExcelHandlerAdapter {
    /**
     * 格式化
     *
     * @param value 单元格数据值
     * @param args  excel注解args参数组
     * @return 处理后的值
     */
    Object format(Object value, String[] args);
}
