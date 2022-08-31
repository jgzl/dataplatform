import { AxiosResponse } from "axios";
import { App } from "@vue/runtime-core";
import request from "./axios.config";
import {ElMessage} from "element-plus/es";

export interface HttpOption {
  url: string;
  data?: any;
  method?: string;
  headers?: any;
  beforeRequest?: () => void;
  afterRequest?: () => void;
}

export interface Response {
  code: number;
  msg: string;
  data: any;
}

function http({
  url,
  data,
  method,
  headers,
  beforeRequest,
  afterRequest,
}: HttpOption) {
  const successHandler = (res: AxiosResponse<Response>) => {
    if (res.data.code === 200) {
      return res.data;
    }
    ElMessage.error(res.data.msg)
    throw new Error(res.data.msg || "请求失败，未知异常");
  };
  const failHandler = (error: Response) => {
    afterRequest && afterRequest();
    ElMessage.error(error.data.msg)
    throw new Error(error.msg || "请求失败，未知异常");
  };
  beforeRequest && beforeRequest();
  method = method || "GET";
  const params = Object.assign(typeof data === 'function' ? data() : data || {}, {})
  switch (method) {
    case "GET":
      return request
          .get(url, { params })
          .then(successHandler, failHandler);
    case "POST":
      return request
          .post(url, params, { headers: headers })
          .then(successHandler, failHandler);
    case "PUT":
      return request
          .put(url, params, { headers: headers })
          .then(successHandler, failHandler);
    case "DELETE":
      return request
          .delete(url, { params })
          .then(successHandler, failHandler);
    default:
      return request
          .get(url, { params })
          .then(successHandler, failHandler);
  }
}

export function get({
  url,
  data,
  method = "GET",
  beforeRequest,
  afterRequest,
}: HttpOption): Promise<Response> {
  return http({
    url,
    method,
    data,
    beforeRequest,
    afterRequest,
  });
};

export function post({
  url,
  data,
  method = "POST",
  headers,
  beforeRequest,
  afterRequest,
}: HttpOption): Promise<Response> {
  return http({
    url,
    method,
    data,
    headers,
    beforeRequest,
    afterRequest,
  });
};

export function put({
  url,
  data,
  method = "PUT",
  headers,
  beforeRequest,
  afterRequest,
}: HttpOption): Promise<Response> {
  return http({
    url,
    method,
    data,
    headers,
    beforeRequest,
    afterRequest,
  });
};

export function httpDelete({
  url,
  data,
  method = "DELETE",
  headers,
  beforeRequest,
  afterRequest,
}: HttpOption): Promise<Response> {
  return http({
    url,
    method,
    data,
    headers,
    beforeRequest,
    afterRequest,
  });
};

function install(app: App): void {
  app.config.globalProperties.$http = http;

  app.config.globalProperties.$get = get

  app.config.globalProperties.$post = post

  app.config.globalProperties.$put = put

  app.config.globalProperties.$delete = httpDelete
}

export default {
  install,
  get,
  post,
  put,
  httpDelete,
};

declare module "@vue/runtime-core" {
  // 为 `this.$` 提供类型声明
  interface ComponentCustomProperties {
    $get: (options: HttpOption) => Promise<Response>;
    $post: (options: HttpOption) => Promise<Response>;
    $put: (options: HttpOption) => Promise<Response>;
    $delete: (options: HttpOption) => Promise<Response>;
  }
}
