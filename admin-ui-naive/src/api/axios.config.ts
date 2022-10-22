import Axios, {AxiosResponse} from 'axios'
import qs from 'qs'
import useUserStore from '@/store/modules/user'

const TOKEN_HEADER = 'Authorization'

const TOKEN_HEADER_PREFIX = 'Bearer '

// @ts-ignore
export const BASEURL: string = import.meta.env.VITE_APP_API_BASEURL

console.log(import.meta.env)

export const ENV = import.meta.env.NODE_ENV

export const HEADERS = {
  'x-business-api-key': import.meta.env.VITE_APP_API_KEY,
  'x-business-api-secret': import.meta.env.VITE_APP_API_SECRET,
  'x-business-api-system': import.meta.env.VITE_APP_API_SYSTEM,
  'x-business-api-env': import.meta.env.VITE_APP_API_ENV,
}

export const CONTENT_TYPE = 'Content-Type'

export const FORM_URLENCODED = 'application/x-www-form-urlencoded;charset=UTF-8'

export const APPLICATION_JSON = 'application/json;charset=UTF-8'

export const TEXT_PLAIN = 'text/plain;charset=UTF-8'

const service = Axios.create({
  baseURL: BASEURL,
  timeout: 10 * 60 * 1000,
  withCredentials: true, // 跨域请求时发送cookie
})

service.interceptors.request.use(
  (config) => {
    !config.headers && (config.headers = {})
    if (!config.headers[CONTENT_TYPE]) {
      config.headers[CONTENT_TYPE] = APPLICATION_JSON
    }
    if (config.headers[CONTENT_TYPE] === FORM_URLENCODED) {
      config.data = qs.stringify(config.data)
    }
    const token = useUserStore().token
    if (token) {
      config.headers[TOKEN_HEADER] = TOKEN_HEADER_PREFIX + token
    }
    Object.assign(config.headers, HEADERS)
    return config
  },
  (error) => {
    return Promise.reject(error.response)
  }
)

service.interceptors.response.use(
  (response: AxiosResponse): AxiosResponse => {
    if (response.status === 200 && response.data.code === 200) {
      return response
    } else {
      throw new Error(response.status.toString())
    }
  },
  (error) => {
    if (ENV === 'development') {
      console.log(error)
    }
    return Promise.reject({ code: 500, msg: '服务器异常，请稍后重试…' })
  }
)

export default service
