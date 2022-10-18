import config from "@/config"
import http from "@/utils/request"

export default {
	ver: {
		url: `${config.API_URL}/admin/demo/ver`,
		name: "获取最新版本号",
		get: async function() {
			return await config.APP_VER;
		}
		// get: async function(params){
		//	return await http.get(this.url, params);
		//}
	},
	post: {
		url: `${config.API_URL}/admin/demo/post`,
		name: "分页列表",
		post: async function(data){
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	page: {
		url: `${config.API_URL}/admin/demo/page`,
		name: "分页列表",
		get: async function(params){
			return await http.get(this.url, params);
		}
	},
	list: {
		url: `${config.API_URL}/admin/demo/list`,
		name: "数据列表",
		get: async function(params){
			return await http.get(this.url, params);
		}
	},
	menu: {
		url: `${config.API_URL}/admin/demo/menu`,
		name: "普通用户菜单",
		get: async function(){
			return await http.get(this.url);
		}
	},
	status: {
		url: `${config.API_URL}/admin/demo/status`,
		name: "模拟无权限",
		get: async function(code){
			return await http.get(this.url, {}, {
				headers: {
					"response-status": code
				}
			});
		}
	}
}
