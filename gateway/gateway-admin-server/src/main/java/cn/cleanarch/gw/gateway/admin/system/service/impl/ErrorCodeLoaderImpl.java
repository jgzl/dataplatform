package cn.cleanarch.gw.gateway.admin.system.service.impl;

import cn.cleanarch.gw.common.core.utils.DateUtils;
import cn.cleanarch.gw.gateway.admin.system.dto.SysErrorCodeRespDTO;
import cn.cleanarch.gw.gateway.admin.system.service.ErrorCodeFrameworkService;
import cn.cleanarch.gw.gateway.admin.system.service.ErrorCodeLoader;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ErrorCodeLoader 的实现类，从 infra 的数据库中，加载错误码。
 *
 * 考虑到错误码会刷新，所以按照 {@link #REFRESH_ERROR_CODE_PERIOD} 频率，增量加载错误码。
 *
 * @author lihaifeng
 */
@RequiredArgsConstructor
@Slf4j
public class ErrorCodeLoaderImpl implements ErrorCodeLoader {

    /**
     * 刷新错误码的频率，单位：毫秒
     */
    private static final int REFRESH_ERROR_CODE_PERIOD = 60 * 1000;

    /**
     * 应用分组
     */
    private final String applicationName;
    /**
     * 错误码 Service
     */
    private final ErrorCodeFrameworkService errorCodeService;

    /**
     * 缓存错误码的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private LocalDateTime maxUpdateTime;

    @EventListener(ApplicationReadyEvent.class)
    public void loadErrorCodes() {
        this.loadErrorCodes0();
    }

    @Scheduled(fixedDelay = REFRESH_ERROR_CODE_PERIOD, initialDelay = REFRESH_ERROR_CODE_PERIOD)
    public void refreshErrorCodes() {
        this.loadErrorCodes0();
    }

    private void loadErrorCodes0() {
        // 加载错误码
        List<SysErrorCodeRespDTO> sysErrorCodeRespDTOS = errorCodeService.getErrorCodeList(applicationName, maxUpdateTime);
        if (CollUtil.isEmpty(sysErrorCodeRespDTOS)) {
            return;
        }
        log.info("[loadErrorCodes0][加载到 ({}) 个错误码]", sysErrorCodeRespDTOS.size());

        // 刷新错误码的缓存
        sysErrorCodeRespDTOS.forEach(errorCodeRespDTO -> {
            // 写入到错误码的缓存
            putErrorCode(errorCodeRespDTO.getCode(), errorCodeRespDTO.getMessage());
            // 记录下更新时间，方便增量更新
            maxUpdateTime = DateUtils.max(maxUpdateTime, errorCodeRespDTO.getUpdateTime());
        });
    }

}
