import config from "@/config"
import http from "@/utils/request"

export default {
	token: {
		url: `${config.API_URL}/user/login`,
		name: "登录获取TOKEN",
		post: async function(data={}){
			return await http.post(this.url, data);
		}
	},
	currentUserInfo: {
		url: `${config.API_URL}/system/user/info`,
		name: "登录获取TOKEN",
		get: async function(data){
			return await http.get(`${this.url}/${data}`);
		}
	}
}
