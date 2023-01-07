import config from "@/config"
import http from "@/utils/request"

export default {
	menu: {
		myMenus: {
			url: `${config.API_URL}/system-center/system/menu/tree/user`,
			name: "获取我的菜单",
			get: async function(){
				return await http.get(this.url);
			}
		},
		tree: {
			url: `${config.API_URL}/system-center/system/menu/tree`,
			name: "获取菜单-树",
			get: async function(){
				return await http.get(this.url);
			}
		},
		list: {
			url: `${config.API_URL}/system-center/system/menu/list`,
			name: "获取菜单",
			get: async function(){
				return await http.get(this.url);
			}
		},
		listByRole: {
			url: `${config.API_URL}/system-center/system/menu/list`,
			name: "获取菜单",
			get: async function(param){
				return await http.get(`${this.url}/${param}`);
			}
		},
		save: {
			url: `${config.API_URL}/system-center/system/menu`,
			name: "保存菜单",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system-center/system/menu`,
			name: "更新菜单",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system-center/system/menu`,
			name: "删除菜单",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	dic: {
		tree: {
			url: `${config.API_URL}/system-center/system/dic/tree`,
			name: "获取字典树",
			get: async function(){
				return await http.get(this.url);
			}
		},
		list: {
			url: `${config.API_URL}/system-center/system/dic/list`,
			name: "字典明细",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		get: {
			url: `${config.API_URL}/system-center/system/dic/get`,
			name: "获取字典数据",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	role: {
		page: {
			url: `${config.API_URL}/system-center/system/role/page`,
			name: "获取角色列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/system-center/system/role/list`,
			name: "获取角色列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/system-center/system/role`,
			name: "保存角色",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system-center/system/role`,
			name: "更新角色",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system-center/system/role`,
			name: "删除角色",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		},
		menu: {
			url: `${config.API_URL}/system-center/system/role/menu`,
			name: "更新角色-菜单",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
	},
	dept: {
		tree: {
			url: `${config.API_URL}/system-center/system/dept/tree`,
			name: "获取部门列表-树",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/system-center/system/dept`,
			name: "保存部门",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system-center/system/dept`,
			name: "更新部门",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system-center/system/dept`,
			name: "删除部门",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	user: {
		page: {
			url: `${config.API_URL}/system-center/system/user/page`,
			name: "获取用户列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/system-center/system/user/list`,
			name: "获取用户列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/system-center/system/user`,
			name: "保存用户",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system-center/system/user`,
			name: "更新用户",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system-center/system/user`,
			name: "删除用户",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	app: {
		list: {
			url: `${config.API_URL}/system-center/system/app/list`,
			name: "应用列表",
			get: async function(){
				return await http.get(this.url);
			}
		}
	},
	log: {
		list: {
			url: `${config.API_URL}/system-center/system/log/list`,
			name: "日志列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	table: {
		list: {
			url: `${config.API_URL}/system-center/system/table/list`,
			name: "表格列管理列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		info: {
			url: `${config.API_URL}/system-center/system/table/info`,
			name: "表格列管理详情",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	tasks: {
		list: {
			url: `${config.API_URL}/system-center/system/tasks/list`,
			name: "系统任务管理",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	errorCode: {
		page: {
			url: `${config.API_URL}/system-center/system/error-code/page`,
			name: "获取错误码列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/system-center/system/error-code/list`,
			name: "获取错误码列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/system-center/system/error-code`,
			name: "保存错误码",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system-center/system/error-code`,
			name: "更新错误码",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system-center/system/error-code`,
			name: "删除错误码",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	}
}
