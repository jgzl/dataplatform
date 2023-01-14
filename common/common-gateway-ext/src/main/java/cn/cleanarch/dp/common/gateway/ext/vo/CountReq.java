package cn.cleanarch.dp.common.gateway.ext.vo;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author JL
 * @Date 2020/07/09
 * @Version V1.0
 */
@Data
public class CountReq implements java.io.Serializable {
    /**
     * 负载ID
     */
    private String balancedId;
    /**
     * 路由集合
     */
    private List<String> routeIds;
    /**
     * 统计类型
     */
    private String dateType;
    /**
     * 名称
     */
    private String name;
    /**
     * 日期
     */
    private String date;
    /**
     * 分组
     */
    private String groupCode;

    private Integer currentPage;
    private Integer pageSize;
}
