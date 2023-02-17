package cn.cleanarch.dp.common.job.model;

public enum ExecutorRouteStrategyEnum {

    FIRST("FIRST", "第一个"),
    LAST("LAST", "最后一个"),
    ROUND("ROUND", "轮训"),
    RANDOM("RANDOM", "随机"),
    CONSISTENT_HASH("CONSISTENT_HASH", "一致性hash"),
    LEAST_FREQUENTLY_USED("LEAST_FREQUENTLY_USED", "最不经常使用"),
    LEAST_RECENTLY_USED("LEAST_RECENTLY_USED", "最近最久未使用"),
    FAILOVER("FAILOVER", "故障转移"),
    BUSYOVER("BUSYOVER", "忙碌转义"),
    SHARDING_BROADCAST("SHARDING_BROADCAST", "分片广播");

    private String strategy;
    private String desc;

    ExecutorRouteStrategyEnum(String strategy, String desc) {
        this.strategy = strategy;
        this.desc = desc;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}