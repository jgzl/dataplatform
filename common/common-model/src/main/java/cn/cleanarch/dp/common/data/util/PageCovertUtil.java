package cn.cleanarch.dp.common.data.util;

import cn.cleanarch.dp.common.core.model.PageResult;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

public class PageCovertUtil {
    /**
     * 将PageInfo对象泛型中的Po对象转化为Vo对象
     *
     * @param pageInfoPo PageInfo<Po>对象</>
     * @param <V>        V类型
     * @return
     */
    public static <P,V> PageResult<V> pageVoCovert(PageResult<P> pageInfoPo, Class<V> v) {
        // 创建Page对象，实际上是一个ArrayList类型的集合
        try {
            if (pageInfoPo != null) {

                List<V> list = new ArrayList<>();
                PageResult<V> page = new PageResult<>(pageInfoPo.getTotal(),list);
                List<P> records = pageInfoPo.getData();
                for (P record : records) {
                    if(record!=null){
                        V newV = v.newInstance();
                        // 把原对象数据拷贝到新的对象
                        BeanUtil.copyProperties(record,newV);
                        list.add(newV);
                    }
                }
                return page;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}