//ip_api.js
import { service } from "@/utils/requestData"


/**
 * 添加网关路由服务
 */
export const addIp = data => {
    return service({
        url: '/gateway-admin/gateway/ip/add',
        method: 'post',
        data
    })
};

/**
 * 添加网关路由服务
 */
export const updateIp = data => {
    return service({
        url: '/gateway-admin/gateway/ip/update',
        method: 'post',
        data
    })
};

/**
 * 查询网关路由列表
 */
export const ipPageList = data => {
    return service({
        url: '/gateway-admin/gateway/ip/pageList',
        method: 'post',
        data
    })
};

/**
 * 删除网关路由
 */
export const deleteIp = data => {
    return service({
        url: '/gateway-admin/gateway/ip/delete',
        method: 'get',
        data
    })
};
