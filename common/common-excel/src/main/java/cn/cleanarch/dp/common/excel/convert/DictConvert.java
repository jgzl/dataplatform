package cn.cleanarch.dp.common.excel.convert;

import cn.cleanarch.dp.common.excel.annotations.DictFormat;
import cn.hutool.core.convert.Convert;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel 数据字典转换器
 *
 * @author lihaifeng
 */
@Slf4j
public class DictConvert implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        throw new UnsupportedOperationException("暂不支持，也不需要");
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        throw new UnsupportedOperationException("暂不支持，也不需要");
    }

    @Override
    public Object convertToJavaData(ReadCellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        // 使用字典解析
        String type = getType(contentProperty);
        String label = cellData.getStringValue();
//        DictDataRespDTO dictData = DictFrameworkUtils.parseDictDataFromCache(type, label);
//        if (dictData == null) {
//            log.error("[convertToJavaData][type({}) 解析不掉 label({})]", type, label);
//            return null;
//        }
        // 将 String 的 value 转换成对应的属性
        Class<?> fieldClazz = contentProperty.getField().getType();
        return Convert.convert(fieldClazz, new Object());
    }

    @Override
    public WriteCellData<String> convertToExcelData(Object object, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        // 空时，返回空
        if (object == null) {
            WriteCellData<String> WriteCellData = new WriteCellData<>();
            WriteCellData.setData("");
            return WriteCellData;
        }

        // 使用字典格式化
        String type = getType(contentProperty);
        String value = String.valueOf(object);
//        DictDataRespDTO dictData = DictFrameworkUtils.getDictDataFromCache(type, value);
//        if (dictData == null) {
//            log.error("[convertToExcelData][type({}) 转换不了 label({})]", type, value);
//            WriteCellData<String> WriteCellData = new WriteCellData<>();
//            WriteCellData.setData("");
//            return WriteCellData;
//        }
        // 生成 Excel 小表格
        WriteCellData<String> WriteCellData = new WriteCellData<>();
        WriteCellData.setData("");
        return WriteCellData;
    }

    private static String getType(ExcelContentProperty contentProperty) {
        return contentProperty.getField().getAnnotation(DictFormat.class).value();
    }

}
