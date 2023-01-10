package cn.cleanarch.dp.flink;

import java.util.concurrent.ConcurrentHashMap;

public class DataObjectSyncCacheHolder {
    private static ConcurrentHashMap<String,DataObjectSyncDO> cache = new ConcurrentHashMap<>();

    public DataObjectSyncDO get(String key) {
        return cache.get(key);
    }

    public static void put(String key,DataObjectSyncDO dataObjectSyncDO) {
        cache.put(key,dataObjectSyncDO);
    }

    public static void clear() {
        cache.clear();
    }
}
