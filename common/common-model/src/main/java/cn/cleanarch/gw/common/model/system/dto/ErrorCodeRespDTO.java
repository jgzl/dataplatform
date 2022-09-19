package cn.cleanarch.gw.common.model.system.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 错误码的 Response DTO
 *
 * @author lihaifeng
 */
@Data
public class ErrorCodeRespDTO {

    /**
     * 错误码编码
     */
    private Integer code;
    /**
     * 错误码错误提示
     */
    private String message;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
