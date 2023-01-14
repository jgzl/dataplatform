//monitor_api.js
import { service } from "@/utils/requestData"


/**
 * 查询监控列表
 */
 export const monitorList = data => {
    return service({
        url: '/gateway-admin/gateway/monitor/list',
        method: 'post',
        data
    })
};

/**
 * 关闭本次告警
 */
 export const closeMonitor = data => {
    return service({
        url: '/gateway-admin/gateway/monitor/close',
        method: 'get',
        data
    })
};
