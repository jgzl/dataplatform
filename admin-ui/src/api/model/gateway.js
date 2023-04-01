import {http} from "@/utils/request"

export default {
	log_page: {
		url: `/gateway-admin/gateway/log/search`,
		name: "获取网关日志列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	log_list: {
		url: `/gateway-admin/gateway/log/list`,
		name: "获取网关日志列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	log_save: {
		url: `/gateway-admin/gateway/log`,
		name: "保存网关日志",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	log_update: {
		url: `/gateway-admin/gateway/log`,
		name: "更新网关日志",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	log_delete: {
		url: `/gateway-admin/gateway/log`,
		name: "删除网关日志",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	route_page: {
		url: `/gateway-admin/gateway/route/page`,
		name: "获取网关路由列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	route_list: {
		url: `/gateway-admin/gateway/route/list`,
		name: "获取网关路由列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	route_save: {
		url: `/gateway-admin/gateway/route`,
		name: "保存网关路由",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	route_update: {
		url: `/gateway-admin/gateway/route`,
		name: "更新网关路由",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	route_delete: {
		url: `/gateway-admin/gateway/route`,
		name: "删除网关路由",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	access_page: {
		url: `/gateway-admin/gateway/access/page`,
		name: "获取网关访问列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	access_list: {
		url: `/gateway-admin/gateway/access/list`,
		name: "获取网关访问列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	access_save: {
		url: `/gateway-admin/gateway/access`,
		name: "保存网关访问",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	access_update: {
		url: `/gateway-admin/gateway/access`,
		name: "更新网关访问",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	access_delete: {
		url: `/gateway-admin/gateway/access`,
		name: "删除网关访问",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	metadata_page: {
		url: `/gateway-admin/gateway/metadata/page`,
		name: "获取网关元数据列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	metadata_save: {
		url: `/gateway-admin/gateway/metadata`,
		name: "保存网关元数据",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	metadata_update: {
		url: `/gateway-admin/gateway/metadata`,
		name: "更新网关元数据",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	metadata_delete: {
		url: `/gateway-admin/gateway/metadata`,
		name: "删除网关元数据",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	metadata_importFile: {
		url: `/gateway-admin/gateway/metadata/import`,
		name: "导出网关元数据列表",
		post: async function (data, config = {}) {
			return await http.post(this.url, data, config);
		}
	},
	metadata_exportFile: {
		url: `/gateway-admin/gateway/metadata/export`,
		name: "导出网关元数据列表",
		get: async function (data, config = {}) {
			return await http.get(this.url, data, config);
		}
	},
	application_page: {
		url: `/gateway-admin/gateway/application/page`,
		name: "获取网关应用服务表列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	application_save: {
		url: `/gateway-admin/gateway/application`,
		name: "保存网关应用服务表",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	application_update: {
		url: `/gateway-admin/gateway/application`,
		name: "更新网关应用服务表",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	application_delete: {
		url: `/gateway-admin/gateway/application`,
		name: "删除网关应用服务表",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
}
