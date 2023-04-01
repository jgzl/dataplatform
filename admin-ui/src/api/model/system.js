import {http} from "@/utils/request"

export default {
	menu_myMenus: {
		url: `/system-center/system/menu/tree/user`,
		name: "获取我的菜单",
		get: async function () {
			return await http.get(this.url);
		}
	},
	menu_tree: {
		url: `/system-center/system/menu/tree`,
		name: "获取菜单-树",
		get: async function () {
			return await http.get(this.url);
		}
	},
	menu_list: {
		url: `/system-center/system/menu/list`,
		name: "获取菜单",
		get: async function () {
			return await http.get(this.url);
		}
	},
	menu_listByRole: {
		url: `/system-center/system/menu/list`,
		name: "获取菜单",
		get: async function (param) {
			return await http.get(`${this.url}/${param}`);
		}
	},
	menu_save: {
		url: `/system-center/system/menu`,
		name: "保存菜单",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	menu_update: {
		url: `/system-center/system/menu`,
		name: "更新菜单",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	menu_delete: {
		url: `/system-center/system/menu`,
		name: "删除菜单",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	dic_tree: {
		url: `/system-center/system/dic/tree`,
		name: "获取字典树",
		get: async function () {
			return await http.get(this.url);
		}
	},
	dic_list: {
		url: `/system-center/system/dic/list`,
		name: "字典明细",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	dic_get: {
		url: `/system-center/system/dic/get`,
		name: "获取字典数据",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	role_page: {
		url: `/system-center/system/role/page`,
		name: "获取角色列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	role_list: {
		url: `/system-center/system/role/list`,
		name: "获取角色列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	role_save: {
		url: `/system-center/system/role`,
		name: "保存角色",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	role_update: {
		url: `/system-center/system/role`,
		name: "更新角色",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	role_delete: {
		url: `/system-center/system/role`,
		name: "删除角色",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	role_menu: {
		url: `/system-center/system/role/menu`,
		name: "更新角色-菜单",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	dept_tree: {
		url: `/system-center/system/dept/tree`,
		name: "获取部门列表-树",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	dept_save: {
		url: `/system-center/system/dept`,
		name: "保存部门",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	dept_update: {
		url: `/system-center/system/dept`,
		name: "更新部门",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	dept_delete: {
		url: `/system-center/system/dept`,
		name: "删除部门",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	user_page: {
		url: `/system-center/system/user/page`,
		name: "获取用户列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	user_list: {
		url: `/system-center/system/user/list`,
		name: "获取用户列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	user_save: {
		url: `/system-center/system/user`,
		name: "保存用户",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	user_update: {
		url: `/system-center/system/user`,
		name: "更新用户",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	user_delete: {
		url: `/system-center/system/user`,
		name: "删除用户",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	},
	app_list: {
		url: `/system-center/system/app/list`,
		name: "应用列表",
		get: async function () {
			return await http.get(this.url);
		}
	},
	log_list: {
		url: `/system-center/system/log/list`,
		name: "日志列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	table_list: {
		url: `/system-center/system/table/list`,
		name: "表格列管理列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	table_info: {
		url: `/system-center/system/table/info`,
		name: "表格列管理详情",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	tasks_list: {
		url: `/system-center/system/tasks/list`,
		name: "系统任务管理",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	errorCode_page: {
		url: `/system-center/system/error-code/page`,
		name: "获取错误码列表-分页",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	errorCode_list: {
		url: `/system-center/system/error-code/list`,
		name: "获取错误码列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	errorCode_save: {
		url: `/system-center/system/error-code`,
		name: "保存错误码",
		post: async function (params) {
			return await http.post(this.url, params);
		}
	},
	errorCode_update: {
		url: `/system-center/system/error-code`,
		name: "更新错误码",
		put: async function (params) {
			return await http.put(this.url, params);
		}
	},
	errorCode_delete: {
		url: `/system-center/system/error-code`,
		name: "删除错误码",
		delete: async function (params) {
			return await http.delete(`${this.url}/${params}`);
		}
	}
}
