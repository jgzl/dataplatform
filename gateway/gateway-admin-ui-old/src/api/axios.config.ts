import Axios, { AxiosResponse } from "axios";
import qs from "qs";
import Cookies from 'js-cookie'
import {
    USER_INFO_KEY,
    USER_TOKEN_KEY,
} from "@/store/keys";
import {ElMessage} from "element-plus";

const TOKEN_HEADER = "Authorization";

const TOKEN_HEADER_PREFIX = "Bearer ";

export const CONTENT_TYPE = "Content-Type";

export const FORM_URLENCODED =
  "application/x-www-form-urlencoded;charset=UTF-8";

export const APPLICATION_JSON = "application/json;charset=UTF-8";

export const TEXT_PLAIN = "text/plain;charset=UTF-8";

const service = Axios.create({
  baseURL: process.env.VUE_APP_BASE_API,
  timeout: 10 * 60 * 1000,
  withCredentials: true, // 跨域请求时发送cookie
});

service.interceptors.request.use(
  (config) => {
    !config.headers && (config.headers = {});
    if (!config.headers[CONTENT_TYPE]) {
      config.headers[CONTENT_TYPE] = APPLICATION_JSON;
    }
    if (config.headers[CONTENT_TYPE] === FORM_URLENCODED) {
      config.data = qs.stringify(config.data);
    }
    config.headers[TOKEN_HEADER] = TOKEN_HEADER_PREFIX+Cookies.get(USER_TOKEN_KEY);
    return config;
  },
  (error) => {
    return Promise.reject(error.response);
  }
);

service.interceptors.response.use(
  (response: AxiosResponse): AxiosResponse => {
    if (response.status === 200 && response.data.code === 200) {
      return response;
    } else {
      ElMessage.error(response.data.msg)
      throw new Error(response.status.toString());
    }
  },
  (error) => {
    if (process.env.NODE_ENV === "development") {
      console.log(error);
    }
    return Promise.reject({ code: 500, msg: "服务器异常，请稍后重试…" });
  }
);

export default service;
