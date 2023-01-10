package cn.cleanarch.dp.gateway.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

/**
 * 网关日志
 *
 * @author li7hai26@gmail.com
 * @date 2021/12/16
 */
@TableName("gateway_log")
@Data
@Setting
@Document(indexName = "gateway_log")
public class GatewayLogDO {

    /**
     * 主键ID
     */
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 请求来源服务
     */
    private String sourceService;

    /**
     * 请求来源key
     */
    private String apiKey;

    /**
     * 请求来源secret
     */
    private String apiSecret;

    /**
     * 请求来源环境
     */
    private String environment;

    /**
     * 目标服务
     */
    private String targetService;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求路径参数
     */
    private String requestPathAndQuery;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求头
     */
    @Field(type = FieldType.Text)
    private String requestHeader;

    /**
     * 请求源IP
     */
    private String requestSourceIp;

    /**
     * 请求参数值
     */
    @Field(type = FieldType.Text)
    private String requestBody;

    /**
     * 返回参数值
     */
    @Field(type = FieldType.Text)
    private String responseBody;

    /**
     * 请求时长ms
     */
    private Long executeTime;

    /**
     * 请求返回HTTP状态码
     */
    private String httpStatus;

    /**
     * 请求错误信息
     */
    @Field(type = FieldType.Text)
    private String errorMsg;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, name = "@requestTime")
    private LocalDateTime requestTime;

    /**
     * 更新时间
     */
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, name = "@responseTime")
    private LocalDateTime responseTime;

    /**
     * 时间戳
     */
    @TableField(exist = false)
    @JsonProperty(value = "@timestamp")
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, name = "@timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();
}