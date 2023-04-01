import {http} from "@/utils/request";

export default {
	saveApiDoc: {
		url: '/gateway-admin/gateway/apiDoc/save',
		name: "保存API接口内容",
		request: async function (data, config = {}) {
			return await http.post(this.url, data, config);
		}
	},
	findByApiDoc: {
		url: '/gateway-admin/gateway/apiDoc/findById',
		name: "保存API接口内容",
		request: async function (data, config = {}) {
			return await http.get(this.url, data, config);
		}
	}
}
