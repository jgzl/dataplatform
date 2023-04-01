import {http} from "@/utils/request"

export default {
	appVersion: {
		url: `/gateway-admin/demo/appVersion`,
		name: "获取最新版本号",
		get: async function () {
			return await config.ver;
		}
		// get: async function(params){
		//	return await http.get(this.url, params);
		//}
	},
	mockTableList: {
		url: `/gateway-admin/demo/mockTableList`,
		name: "分页列表",
		post: async function (data) {
			return await http.post(this.url, data, {
				headers: {
					//'response-status': 401
				}
			});
		}
	},
	mockPage: {
		url: `/gateway-admin/demo/mockPage`,
		name: "分页列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	mockList: {
		url: `/gateway-admin/demo/mockList`,
		name: "数据列表",
		get: async function (params) {
			return await http.get(this.url, params);
		}
	},
	mockHttpStatus: {
		url: `/gateway-admin/demo/mockHttpStatus`,
		name: "模拟无权限",
		get: async function (code) {
			return await http.get(this.url, {}, {
				headers: {
					"response-status": code
				}
			});
		}
	}
}
