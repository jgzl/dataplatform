import config from "@/config"
import http from "@/utils/request"

export default {
	log: {
		page: {
			url: `${config.API_URL}/gateway-admin/gateway/log/search`,
			name: "获取网关日志列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/gateway-admin/gateway/log/list`,
			name: "获取网关日志列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/gateway-admin/gateway/log`,
			name: "保存网关日志",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/gateway-admin/gateway/log`,
			name: "更新网关日志",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/gateway-admin/gateway/log`,
			name: "删除网关日志",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	route: {
		page: {
			url: `${config.API_URL}/gateway-admin/gateway/route/page`,
			name: "获取网关路由列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/gateway-admin/gateway/route/list`,
			name: "获取网关路由列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/gateway-admin/gateway/route`,
			name: "保存网关路由",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/gateway-admin/gateway/route`,
			name: "更新网关路由",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/gateway-admin/gateway/route`,
			name: "删除网关路由",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	access: {
		page: {
			url: `${config.API_URL}/gateway-admin/gateway/access/page`,
			name: "获取网关访问列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/gateway-admin/gateway/access/list`,
			name: "获取网关访问列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/gateway-admin/gateway/access`,
			name: "保存网关访问",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/gateway-admin/gateway/access`,
			name: "更新网关访问",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/gateway-admin/gateway/access`,
			name: "删除网关访问",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
}
