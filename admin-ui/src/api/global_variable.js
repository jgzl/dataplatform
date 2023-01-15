// global_variable.js
// 专门放置 全局变量

const errMsg = "操作失败"
const successMsg = "操作成功"
const contentType = "application/json;charset=UTF-8"
const systemVersion = "snapshot-nacos.v.3.2"
const gatewayRoutesURL = process.env.VUE_APP_GATEWAY_URL

// 分组类型，暂不放到数据字典，直接由前端添加
const groups = [
		{ value: 'public_api', label: '公共API' },
		{ value: 'external_api', label: '第三方API' },
		{ value: 'interior_api', label: '内网API' },
		{ value: 'pay_api', label: '支付API' },
		{ value: 'other_api', label: '其它API' }
	]


//导出全局变量
export default{
	contentType,
	systemVersion,
	gatewayRoutesURL,
	errMsg,
	successMsg,
	groups
}
