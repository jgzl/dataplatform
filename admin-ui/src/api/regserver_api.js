//regserver_api.js
import { service } from "@/utils/requestData"


/**
 * 添加客户端关联的注册服务
 */
export const addRegServer = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/add',
        method: 'post',
        data
    })
};

/**
 * 修改客户端关联的注册服务
 */
export const updateRegServer = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/update',
        method: 'post',
        data
    })
};

/**
 * 查询客户端关联的注册服务分页列表
 */
export const regServerPageList = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/serverPageList',
        method: 'post',
        data
    })
};

/**
 * 查询客户端关联的注册服务分页列表
 */
export const regClientPageList = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/clientPageList',
        method: 'post',
        data
    })
};

/**
 * 启用客户端关联的注册服务访问状态
 */
export const startRegServer = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/start',
        method: 'get',
        data
    })
};

/**
 * 停止客户端关联的注册服务访问状态
 */
export const stopRegServer = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/stop',
        method: 'get',
        data
    })
};

/**
 * 全部启用客户端关联的注册服务访问状态
 */
export const startAllRegServer = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/startClientAllRoute',
        method: 'get',
        data
    })
};

/**
 * 全部停止客户端关联的注册服务访问状态
 */
export const stopAllRegServer = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/stopClientAllRoute',
        method: 'get',
        data
    })
};

/**
 * 取消客户端关联的注册服务
 */
export const deleteRegServer = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/delete',
        method: 'get',
        data
    })
};

/**
 * 获取未注册网关路由列表
 */
export const notRegServerPageList = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/notRegServerPageList',
        method: 'post',
        data
    })
};

// ==========================服务端管理=============================

/**
 * 添加客户端关联的注册服务
 */
export const addRegClient = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/add',
        method: 'post',
        data
    })
};

/**
 * 全部启用客户端关联的注册服务访问状态
 */
export const startAllRegClient = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/startRouteAllClient',
        method: 'get',
        data
    })
};

/**
 * 全部停止客户端关联的注册服务访问状态
 */
export const stopAllRegClient = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/stopRouteAllClient',
        method: 'get',
        data
    })
};

/**
 * 启用客户端关联的注册服务访问状态
 */
export const startRegClient = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/start',
        method: 'get',
        data
    })
};

/**
 * 停止客户端关联的注册服务访问状态
 */
export const stopRegClient = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/stop',
        method: 'get',
        data
    })
};

/**
 * 取消客户端关联的注册服务
 */
export const deleteRegClient = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/delete',
        method: 'get',
        data
    })
};

/**
 * 获取当前网关路由未注册客户端列表
 */
export const notRegClientPageList = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/notRegClientPageList',
        method: 'post',
        data
    })
};

/**
 * 获取当前网关路由未注册客户端列表
 */
export const regClientList = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/regClientList',
        method: 'post',
        data
    })
};

/**
 * 创建当前网关路由注册客户端的TOKEN令牌
 */
 export const createRegClientToken = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/createToken',
        method: 'post',
        data
    })
};

/**
 * 移除当前网关路由注册客户端的TOKEN令牌
 */
 export const removeRegClientToken = data => {
    return service({
        url: '/gateway-admin/gateway/regServer/removeToken',
        method: 'post',
        data
    })
};
