//balanced_api.js
import { service } from "@/utils/requestData"

/**
 * 添加负载配置
 */
export const addBalanced = data => {
    return service({
        url: '/gateway-admin/gateway/balanced/add',
        method: 'post',
        data
    })
};

/**
 * 删除负载配置
 */
export const deleteBalanced = data => {
    return service({
        url: '/gateway-admin/gateway/balanced/delete',
        method: 'get',
        data
    })
};

/**
 * 更新负载配置
 */
export const updateBalanced = data => {
    return service({
        url: '/gateway-admin/gateway/balanced/update',
        method: 'post',
        data
    })
};

/**
 * 查找负载配置
 */
export const findByBalanced = data => {
    return service({
        url: '/gateway-admin/gateway/balanced/findById',
        method: 'get',
        data
    })
};


/**
 * 分页展示负载配置
 */
export const balancedPageList = data => {
    return service({
        url: '/gateway-admin/gateway/balanced/pageList',
        method: 'post',
        data
    })
};

/**
 * 启用负载服务
 */
export const startBalanced = data => {
    return service({
        url: '/gateway-admin/gateway/balanced/start',
        method: 'get',
        data
    })
};

/**
 * 禁用负载服务
 */
export const stopBalanced = data => {
    return service({
        url: '/gateway-admin/gateway/balanced/stop',
        method: 'get',
        data
    })
};

/**
 * 已注册负载服务
 */
export const loadServerRegList = data => {
    return service({
        url: '/gateway-admin/gateway/loadServer/regList',
        method: 'post',
        data
    })
};

/**
 * 未注册负载服务
 */
export const loadServerNotRegList = data => {
    return service({
        url: '/gateway-admin/gateway/loadServer/notRegPageList',
        method: 'post',
        data
    })
};
