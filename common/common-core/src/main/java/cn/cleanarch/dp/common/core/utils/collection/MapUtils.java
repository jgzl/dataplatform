package cn.cleanarch.dp.common.core.utils.collection;

import cn.cleanarch.dp.common.core.model.KeyValue;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.function.Consumer;

/**
 * Map 工具类
 *
 * @author lihaifeng
 */
public class MapUtils {

    /**
     * 从哈希表表中，获得 keys 对应的所有 value 数组
     *
     * @param multimap 哈希表
     * @param keys     keys
     * @return value 数组
     */
    public static <K, V> List<V> getList(Multimap<K, V> multimap, Collection<K> keys) {
        List<V> result = new ArrayList<>();
        keys.forEach(k -> {
            Collection<V> values = multimap.get(k);
            if (CollectionUtil.isEmpty(values)) {
                return;
            }
            result.addAll(values);
        });
        return result;
    }

    /**
     * 从哈希表查找到 key 对应的 value，然后进一步处理
     * 注意，如果查找到的 value 为 null 时，不进行处理
     *
     * @param map      哈希表
     * @param key      key
     * @param consumer 进一步处理的逻辑
     */
    public static <K, V> void findAndThen(Map<K, V> map, K key, Consumer<V> consumer) {
        if (CollUtil.isEmpty(map)) {
            return;
        }
        V value = map.get(key);
        if (value == null) {
            return;
        }
        consumer.accept(value);
    }

    public static <K, V> Map<K, V> convertMap(List<KeyValue<K, V>> keyValues) {
        Map<K, V> map = Maps.newLinkedHashMapWithExpectedSize(keyValues.size());
        keyValues.forEach(keyValue -> map.put(keyValue.getKey(), keyValue.getValue()));
        return map;
    }


    /**
     * 将List中的Key转换为小写
     *
     * @param list 返回新对象
     * @return
     */
    public static List<Map<String, Object>> convertKeyList2LowerCase(List<Map<String, Object>> list) {
        if (null == list) {
            return null;
        }
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        //
        Iterator<Map<String, Object>> iteratorL = list.iterator();
        while (iteratorL.hasNext()) {
            Map<String, Object> map = (Map<String, Object>) iteratorL.next();
            //
            Map<String, Object> result = convertKey2LowerCase(map);
            if (null != result) {
                resultList.add(result);
            }
        }
        //
        return resultList;
    }

    /**
     * 转换单个map,将key转换为小写.
     *
     * @param map 返回新对象
     * @return
     */
    public static Map<String, Object> convertKey2LowerCase(Map<String, Object> map) {
        if (null == map) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //
        Set<String> keys = map.keySet();
        //
        Iterator<String> iteratorK = keys.iterator();
        while (iteratorK.hasNext()) {
            String key = (String) iteratorK.next();
            Object value = map.get(key);
            if (null == key) {
                continue;
            }
            //
            String keyL = key.toLowerCase();
            result.put(keyL, value);
        }
        return result;
    }

    /**
     * 将List中Map的Key转换为小写.
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> trimListKeyValue(List<Map<String, Object>> list) {
        if (null == list) {
            return null;
        }
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        //
        Iterator<Map<String, Object>> iteratorL = list.iterator();
        while (iteratorL.hasNext()) {
            Map<String, Object> map = (Map<String, Object>) iteratorL.next();
            //
            Map<String, Object> result = trimKeyValue(map);
            if (null != result) {
                resultList.add(result);
            }
        }
        //
        return resultList;
    }

    /**
     * 转换单个map,将key转换为小写.
     *
     * @param map 返回新对象
     * @return
     */
    public static Map<String, Object> trimKeyValue(Map<String, Object> map) {
        if (null == map) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        //
        Set<String> keys = map.keySet();
        //
        Iterator<String> iteratorK = keys.iterator();
        while (iteratorK.hasNext()) {
            String key = (String) iteratorK.next();
            Object value = map.get(key);
            if (null == key) {
                continue;
            }
            //
            String keyT = key.trim();
            if (value instanceof String) {
                String valueT = String.valueOf(value).trim();
                result.put(keyT, valueT);
            } else {
                result.put(keyT, value);
            }
        }
        return result;
    }
}