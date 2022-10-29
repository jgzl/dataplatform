import config from "@/config"
import http from "@/utils/request"

export default {
	login: {
		url: `${config.API_URL}/auth/oauth2/token`,
		name: "登录",
		post: async function(data={},headers={}){
			let config = {}
			config.headers = headers
			return await http.post(this.url, null, data, config);
		}
	},
	currentUserInfo: {
		url: `${config.API_URL}/system/user/info`,
		name: "获取当前用户信息",
		get: async function(data){
			return await http.get(`${this.url}/${data}`);
		}
	}
}
