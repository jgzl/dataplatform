//client_api.js
import { service } from "@/utils/requestData"

/**
 * 添加客户端
 */
export const addClient = data => {
    return service({
        url: '/gateway-admin/gateway/client/add',
        method: 'post',
        data
    })
};

/**
 * 更新客户端
 */
export const updateClient = data => {
    return service({
        url: '/gateway-admin/gateway/client/update',
        method: 'post',
        data
    })
};

/**
 * 分页查询客户端列表
 */
export const clientPageList = data => {
    return service({
        url: '/gateway-admin/gateway/client/pageList',
        method: 'post',
        data
    })
};

/**
 * 启用客户端
 */
export const startClient = data => {
    return service({
        url: '/gateway-admin/gateway/client/start',
        method: 'get',
        data
    })
};

/**
 * 停止客户端
 */
export const stopClient = data => {
    return service({
        url: '/gateway-admin/gateway/client/stop',
        method: 'get',
        data
    })
};

/**
 * 删除客户端
 */
export const deleteClient = data => {
    return service({
        url: '/gateway-admin/gateway/client/delete',
        method: 'get',
        data
    })
};
