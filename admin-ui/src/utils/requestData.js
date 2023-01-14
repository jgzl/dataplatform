import axios from 'axios';
import {ElMessageBox, ElNotification} from 'element-plus';
import sysConfig from "@/config";
import tool from '@/utils/tool';
import router from '@/router';

axios.defaults.baseURL = sysConfig.API_URL
axios.defaults.timeout = sysConfig.TIMEOUT
axios.defaults.withCredentials = true

const service = axios

// HTTP request 拦截器
service.interceptors.request.use(
	(config) => {
		let token = tool.cookie.get("TOKEN");
		if(token){
			config.headers[sysConfig.TOKEN_NAME] = sysConfig.TOKEN_PREFIX + token
		}
		if(!sysConfig.REQUEST_CACHE && config.method == 'get'){
			config.params = config.params || config.data || {};
			config.params['_'] = new Date().getTime();
		}
		Object.assign(config.headers, sysConfig.HEADERS)
		return config;
	},
	(error) => {
		return Promise.reject(error);
	}
);

// HTTP response 拦截器
service.interceptors.response.use(
	(response) => {
		return response.data;
	},
	(error) => {
		if (error.response) {
			if (error.response.status === 404) {
				ElNotification.error({
					title: '请求错误',
					message: "Status:404，正在请求不存在的服务器记录！"
				});
			} else if (error.response.status === 400) {
				ElNotification.error({
					title: '请求错误',
					message: error.response.data.msg || "Status:400，服务器发生错误！"
				});
			}else if (error.response.status === 500) {
				ElNotification.error({
					title: '请求错误',
					message: error.response.data.msg || "Status:500，服务器发生错误！"
				});
			} else if (error.response.status === 401) {
				ElMessageBox.confirm('当前用户已被登出或无权限访问当前资源，请尝试重新登录后再操作。', '无权限访问', {
					type: 'error',
					closeOnClickModal: false,
					center: true,
					confirmButtonText: '重新登录'
				}).then(() => {
					router.replace({path: '/login'});
				}).catch(() => {})
			} else {
				ElNotification.error({
					title: '请求错误',
					message: error.message || `Status:${error.response.status}，未知错误！`
				});
			}
		} else {
			ElNotification.error({
				title: '请求错误',
				message: "请求服务器无响应！"
			});
		}

		return Promise.reject(error.response);
	}
);

export { service };
