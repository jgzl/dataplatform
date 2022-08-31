import {post, get, put, httpDelete} from '@/api/http'
import dataTable from './DataTable'
import pageDataTable from "./PageDataTable";
import likeSearch from './LikeSearch'
import baseForm from './Form'
import createScript from './CreateScript'
import echarts from './Echarts'
import setting from '../setting'
import Emit from './Emit'

export function useSetting() {
  return setting
}

export function useDelete() {
  return httpDelete
}

export function usePut() {
  return put
}

export function usePost() {
  return post
}

export function useGet() {
  return get
}

export function useDataTable() {
  return dataTable()
}

export function usePageDataTable() {
  return pageDataTable()
}

export function useLikeSearch() {
  return likeSearch()
}

export function useForm() {
  return baseForm()
}

export function useCreateScript(src: string) {
  return createScript(src)
}

export function useEcharts(dom: HTMLElement, theme?: string) {
  return echarts(dom, theme)
}

export function useEmit() {
  return Emit()
}