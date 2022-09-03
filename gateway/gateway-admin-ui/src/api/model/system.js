import config from "@/config"
import http from "@/utils/request"

// const menuTree = {
// 	"code": 200,
// 	"data": {
// 		"menu": [{
// 			"name": "home",
// 			"path": "/home",
// 			"meta": {
// 				"title": "首页",
// 				"icon": "el-icon-eleme-filled",
// 				"type": "menu"
// 			},
// 			"children": [{
// 				"name": "dashboard",
// 				"path": "/dashboard",
// 				"meta": {
// 					"title": "控制台",
// 					"icon": "el-icon-menu",
// 					"affix": true
// 				},
// 				"component": "home"
// 			}, {
// 				"name": "userCenter",
// 				"path": "/usercenter",
// 				"meta": {
// 					"title": "帐号信息",
// 					"icon": "el-icon-user",
// 				},
// 				"component": "userCenter"
// 			}]
// 		}, {
// 			"name": "vab",
// 			"path": "/vab",
// 			"meta": {
// 				"title": "组件",
// 				"icon": "el-icon-takeaway-box",
// 				"type": "menu"
// 			},
// 			"children": [{
// 				"path": "/vab/mini",
// 				"name": "minivab",
// 				"meta": {
// 					"title": "原子组件",
// 					"icon": "el-icon-magic-stick",
// 					"type": "menu"
// 				},
// 				"component": "vab/mini"
// 			}, {
// 				"path": "/vab/iconfont",
// 				"name": "iconfont",
// 				"meta": {
// 					"title": "扩展图标",
// 					"icon": "el-icon-orange",
// 					"type": "menu"
// 				},
// 				"component": "vab/iconfont"
// 			}, {
// 				"path": "/vab/data",
// 				"name": "vabdata",
// 				"meta": {
// 					"title": "Data 数据展示",
// 					"icon": "el-icon-histogram",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/vab/chart",
// 					"name": "chart",
// 					"meta": {
// 						"title": "图表 Echarts",
// 						"type": "menu"
// 					},
// 					"component": "vab/chart"
// 				}, {
// 					"path": "/vab/statistic",
// 					"name": "statistic",
// 					"meta": {
// 						"title": "统计数值",
// 						"type": "menu"
// 					},
// 					"component": "vab/statistic"
// 				}, {
// 					"path": "/vab/video",
// 					"name": "scvideo",
// 					"meta": {
// 						"title": "视频播放器",
// 						"type": "menu"
// 					},
// 					"component": "vab/video"
// 				}, {
// 					"path": "/vab/qrcode",
// 					"name": "qrcode",
// 					"meta": {
// 						"title": "二维码",
// 						"type": "menu"
// 					},
// 					"component": "vab/qrcode"
// 				}]
// 			}, {
// 				"path": "/vab/form",
// 				"name": "vabform",
// 				"meta": {
// 					"title": "Form 数据录入",
// 					"icon": "el-icon-edit",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/vab/tableselect",
// 					"name": "tableselect",
// 					"meta": {
// 						"title": "表格选择器",
// 						"type": "menu"
// 					},
// 					"component": "vab/tableselect"
// 				}, {
// 					"path": "/vab/formtable",
// 					"name": "formtable",
// 					"meta": {
// 						"title": "表单表格",
// 						"type": "menu"
// 					},
// 					"component": "vab/formtable"
// 				}, {
// 					"path": "/vab/selectFilter",
// 					"name": "selectFilter",
// 					"meta": {
// 						"title": "分类筛选器",
// 						"type": "menu"
// 					},
// 					"component": "vab/selectFilter"
// 				}, {
// 					"path": "/vab/filterbar",
// 					"name": "filterBar",
// 					"meta": {
// 						"title": "过滤器v2",
// 						"type": "menu"
// 					},
// 					"component": "vab/filterBar"
// 				}, {
// 					"path": "/vab/upload",
// 					"name": "upload",
// 					"meta": {
// 						"title": "上传",
// 						"type": "menu"
// 					},
// 					"component": "vab/upload"
// 				}, {
// 					"path": "/vab/select",
// 					"name": "scselect",
// 					"meta": {
// 						"title": "异步选择器",
// 						"type": "menu"
// 					},
// 					"component": "vab/select"
// 				}, {
// 					"path": "/vab/iconselect",
// 					"name": "iconSelect",
// 					"meta": {
// 						"title": "图标选择器",
// 						"type": "menu"
// 					},
// 					"component": "vab/iconselect"
// 				}, {
// 					"path": "/vab/cron",
// 					"name": "cron",
// 					"meta": {
// 						"title": "Cron规则生成器",
// 						"type": "menu"
// 					},
// 					"component": "vab/cron"
// 				}, {
// 					"path": "/vab/editor",
// 					"name": "editor",
// 					"meta": {
// 						"title": "富文本编辑器",
// 						"type": "menu"
// 					},
// 					"component": "vab/editor"
// 				}, {
// 					"path": "/vab/codeeditor",
// 					"name": "codeeditor",
// 					"meta": {
// 						"title": "代码编辑器",
// 						"type": "menu"
// 					},
// 					"component": "vab/codeeditor"
// 				}]
// 			}, {
// 				"path": "/vab/feedback",
// 				"name": "vabfeedback",
// 				"meta": {
// 					"title": "Feedback 反馈",
// 					"icon": "el-icon-mouse",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/vab/drag",
// 					"name": "drag",
// 					"meta": {
// 						"title": "拖拽排序",
// 						"type": "menu"
// 					},
// 					"component": "vab/drag"
// 				}, {
// 					"path": "/vab/contextmenu",
// 					"name": "contextmenu",
// 					"meta": {
// 						"title": "右键菜单",
// 						"type": "menu"
// 					},
// 					"component": "vab/contextmenu"
// 				}, {
// 					"path": "/vab/cropper",
// 					"name": "cropper",
// 					"meta": {
// 						"title": "图像剪裁",
// 						"type": "menu"
// 					},
// 					"component": "vab/cropper"
// 				}, {
// 					"path": "/vab/fileselect",
// 					"name": "fileselect",
// 					"meta": {
// 						"title": "资源库选择器(弃用)",
// 						"type": "menu"
// 					},
// 					"component": "vab/fileselect"
// 				}, {
// 					"path": "/vab/dialog",
// 					"name": "dialogExtend",
// 					"meta": {
// 						"title": "弹窗扩展",
// 						"type": "menu"
// 					},
// 					"component": "vab/dialog"
// 				}]
// 			}, {
// 				"path": "/vab/others",
// 				"name": "vabothers",
// 				"meta": {
// 					"title": "Others 其他",
// 					"icon": "el-icon-more-filled",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/vab/print",
// 					"name": "print",
// 					"meta": {
// 						"title": "打印",
// 						"type": "menu"
// 					},
// 					"component": "vab/print"
// 				}, {
// 					"path": "/vab/watermark",
// 					"name": "watermark",
// 					"meta": {
// 						"title": "水印",
// 						"type": "menu"
// 					},
// 					"component": "vab/watermark"
// 				}, {
// 					"path": "/vab/importexport",
// 					"name": "importexport",
// 					"meta": {
// 						"title": "文件导出导入",
// 						"type": "menu"
// 					},
// 					"component": "vab/importexport"
// 				}]
// 			}, {
// 				"path": "/vab/list",
// 				"name": "list",
// 				"meta": {
// 					"title": "Table 数据列表",
// 					"icon": "el-icon-fold",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/vab/table/base",
// 					"name": "tableBase",
// 					"meta": {
// 						"title": "基础数据列表",
// 						"type": "menu"
// 					},
// 					"component": "vab/table/base"
// 				}, {
// 					"path": "/vab/table/thead",
// 					"name": "tableThead",
// 					"meta": {
// 						"title": "多级表头",
// 						"type": "menu"
// 					},
// 					"component": "vab/table/thead"
// 				}, {
// 					"path": "/vab/table/column",
// 					"name": "tableCustomColumn",
// 					"meta": {
// 						"title": "动态列",
// 						"type": "menu"
// 					},
// 					"component": "vab/table/column"
// 				}, {
// 					"path": "/vab/table/remote",
// 					"name": "tableRemote",
// 					"meta": {
// 						"title": "远程排序过滤",
// 						"type": "menu"
// 					},
// 					"component": "vab/table/remote"
// 				}]
// 			}, {
// 				"path": "/vab/workflow",
// 				"name": "workflow",
// 				"meta": {
// 					"title": "工作流设计器",
// 					"icon": "el-icon-share",
// 					"type": "menu"
// 				},
// 				"component": "vab/workflow"
// 			}, {
// 				"path": "/vab/formrender",
// 				"name": "formRender",
// 				"meta": {
// 					"title": "动态表单(Beta)",
// 					"icon": "el-icon-message-box",
// 					"type": "menu"
// 				},
// 				"component": "vab/form"
// 			}]
// 		}, {
// 			"name": "template",
// 			"path": "/template",
// 			"meta": {
// 				"title": "模板",
// 				"icon": "el-icon-files",
// 				"type": "menu"
// 			},
// 			"children": [{
// 				"path": "/template/layout",
// 				"name": "layoutTemplate",
// 				"meta": {
// 					"title": "布局",
// 					"icon": "el-icon-grid",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/template/layout/blank",
// 					"name": "blank",
// 					"meta": {
// 						"title": "空白模板",
// 						"type": "menu"
// 					},
// 					"component": "template/layout/blank"
// 				}, {
// 					"path": "/template/layout/layoutTCB",
// 					"name": "layoutTCB",
// 					"meta": {
// 						"title": "上中下布局",
// 						"type": "menu"
// 					},
// 					"component": "template/layout/layoutTCB"
// 				}, {
// 					"path": "/template/layout/layoutLCR",
// 					"name": "layoutLCR",
// 					"meta": {
// 						"title": "左中右布局",
// 						"type": "menu"
// 					},
// 					"component": "template/layout/layoutLCR"
// 				}]
// 			}, {
// 				"path": "/template/list",
// 				"name": "list",
// 				"meta": {
// 					"title": "列表",
// 					"icon": "el-icon-document",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/template/list/crud",
// 					"name": "listCrud",
// 					"meta": {
// 						"title": "CRUD",
// 						"type": "menu"
// 					},
// 					"component": "template/list/crud",
// 					"children": [{
// 						"path": "/template/list/crud/detail/:id?",
// 						"name": "listCrud-detail",
// 						"meta": {
// 							"title": "新增/编辑",
// 							"hidden": true,
// 							"active": "/template/list/crud",
// 							"type": "menu"
// 						},
// 						"component": "template/list/crud/detail"
// 					}]
// 				}, {
// 					"path": "/template/list/tree",
// 					"name": "listTree",
// 					"meta": {
// 						"title": "左树右表",
// 						"type": "menu"
// 					},
// 					"component": "template/list/tree"
// 				}, {
// 					"path": "/template/list/tab",
// 					"name": "listTab",
// 					"meta": {
// 						"title": "分类表格",
// 						"type": "menu"
// 					},
// 					"component": "template/list/tab"
// 				}, {
// 					"path": "/template/list/son",
// 					"name": "listSon",
// 					"meta": {
// 						"title": "子母表",
// 						"type": "menu"
// 					},
// 					"component": "template/list/son"
// 				}, {
// 					"path": "/template/list/widthlist",
// 					"name": "widthlist",
// 					"meta": {
// 						"title": "定宽列表",
// 						"type": "menu"
// 					},
// 					"component": "template/list/width"
// 				}]
// 			}, {
// 				"path": "/template/other",
// 				"name": "other",
// 				"meta": {
// 					"title": "其他",
// 					"icon": "el-icon-folder",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "/template/other/stepform",
// 					"name": "stepform",
// 					"meta": {
// 						"title": "分步表单",
// 						"type": "menu"
// 					},
// 					"component": "template/other/stepform"
// 				}]
// 			}]
// 		}, {
// 			"name": "other",
// 			"path": "/other",
// 			"meta": {
// 				"title": "其他",
// 				"icon": "el-icon-more-filled",
// 				"type": "menu"
// 			},
// 			"children": [{
// 				"path": "/other/directive",
// 				"name": "directive",
// 				"meta": {
// 					"title": "指令",
// 					"icon": "el-icon-price-tag",
// 					"type": "menu"
// 				},
// 				"component": "other/directive"
// 			}, {
// 				"path": "/other/viewTags",
// 				"name": "viewTags",
// 				"meta": {
// 					"title": "标签操作",
// 					"icon": "el-icon-files",
// 					"type": "menu"
// 				},
// 				"component": "other/viewTags",
// 				"children": [{
// 					"path": "/other/fullpage",
// 					"name": "fullpage",
// 					"meta": {
// 						"title": "整页路由",
// 						"icon": "el-icon-monitor",
// 						"fullpage": true,
// 						"hidden": true,
// 						"type": "menu"
// 					},
// 					"component": "other/fullpage"
// 				}]
// 			}, {
// 				"path": "/other/verificate",
// 				"name": "verificate",
// 				"meta": {
// 					"title": "表单验证",
// 					"icon": "el-icon-open",
// 					"type": "menu"
// 				},
// 				"component": "other/verificate"
// 			}, {
// 				"path": "/other/loadJS",
// 				"name": "loadJS",
// 				"meta": {
// 					"title": "异步加载JS",
// 					"icon": "el-icon-location-information",
// 					"type": "menu"
// 				},
// 				"component": "other/loadJS"
// 			}, {
// 				"path": "/link",
// 				"name": "link",
// 				"meta": {
// 					"title": "外部链接",
// 					"icon": "el-icon-link",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "https://baidu.com",
// 					"name": "百度",
// 					"meta": {
// 						"title": "百度",
// 						"type": "link"
// 					}
// 				}, {
// 					"path": "https://www.google.cn",
// 					"name": "谷歌",
// 					"meta": {
// 						"title": "谷歌",
// 						"type": "link"
// 					}
// 				}]
// 			}, {
// 				"path": "/iframe",
// 				"name": "Iframe",
// 				"meta": {
// 					"title": "Iframe",
// 					"icon": "el-icon-position",
// 					"type": "menu"
// 				},
// 				"children": [{
// 					"path": "https://v3.cn.vuejs.org",
// 					"name": "vue3",
// 					"meta": {
// 						"title": "VUE 3",
// 						"type": "iframe"
// 					}
// 				}, {
// 					"path": "https://element-plus.gitee.io",
// 					"name": "elementplus",
// 					"meta": {
// 						"title": "Element Plus",
// 						"type": "iframe"
// 					}
// 				}, {
// 					"path": "https://lolicode.gitee.io/scui-doc",
// 					"name": "scuidoc",
// 					"meta": {
// 						"title": "SCUI文档",
// 						"type": "iframe"
// 					}
// 				}]
// 			}]
// 		}, {
// 			"name": "test",
// 			"path": "/test",
// 			"meta": {
// 				"title": "实验室",
// 				"icon": "el-icon-mouse",
// 				"type": "menu"
// 			},
// 			"children": [{
// 				"path": "/test/autocode",
// 				"name": "autocode",
// 				"meta": {
// 					"title": "代码生成器",
// 					"icon": "sc-icon-code",
// 					"type": "menu"
// 				},
// 				"component": "test/autocode/index",
// 				"children": [{
// 					"path": "/test/autocode/table",
// 					"name": "autocode-table",
// 					"meta": {
// 						"title": "CRUD代码生成",
// 						"hidden": true,
// 						"active": "/test/autocode",
// 						"type": "menu"
// 					},
// 					"component": "test/autocode/table"
// 				}]
// 			}, {
// 				"path": "/test/codebug",
// 				"name": "codebug",
// 				"meta": {
// 					"title": "异常处理",
// 					"icon": "sc-icon-bug-line",
// 					"type": "menu"
// 				},
// 				"component": "test/codebug"
// 			}]
// 		}, {
// 			"name": "system",
// 			"path": "/system",
// 			"meta": {
// 				"title": "系统管理",
// 				"icon": "el-icon-setting",
// 				"type": "menu"
// 			},
// 			"children": [{
// 				"path": "/system/setting",
// 				"name": "system",
// 				"meta": {
// 					"title": "系统设置",
// 					"icon": "el-icon-tools",
// 					"type": "menu"
// 				},
// 				"component": "system/setting"
// 			}, {
// 				"path": "/system/user",
// 				"name": "user",
// 				"meta": {
// 					"title": "用户管理",
// 					"icon": "el-icon-user-filled",
// 					"type": "menu"
// 				},
// 				"component": "system/user"
// 			}, {
// 				"path": "/system/role",
// 				"name": "role",
// 				"meta": {
// 					"title": "角色管理",
// 					"icon": "el-icon-notebook",
// 					"type": "menu"
// 				},
// 				"component": "system/role"
// 			}, {
// 				"path": "/system/dept",
// 				"name": "dept",
// 				"meta": {
// 					"title": "部门管理",
// 					"icon": "sc-icon-organization",
// 					"type": "menu"
// 				},
// 				"component": "system/dept"
// 			}, {
// 				"path": "/system/dic",
// 				"name": "dic",
// 				"meta": {
// 					"title": "字典管理",
// 					"icon": "el-icon-document",
// 					"type": "menu"
// 				},
// 				"component": "system/dic"
// 			}, {
// 				"path": "/system/table",
// 				"name": "tableSetting",
// 				"meta": {
// 					"title": "表格列管理",
// 					"icon": "el-icon-scale-to-original",
// 					"type": "menu"
// 				},
// 				"component": "system/table"
// 			}, {
// 				"path": "/system/menu",
// 				"name": "settingMenu",
// 				"meta": {
// 					"title": "菜单管理",
// 					"icon": "el-icon-fold",
// 					"type": "menu"
// 				},
// 				"component": "system/menu"
// 			}, {
// 				"path": "/system/task",
// 				"name": "task",
// 				"meta": {
// 					"title": "计划任务",
// 					"icon": "el-icon-alarm-clock",
// 					"type": "menu"
// 				},
// 				"component": "system/task"
// 			}, {
// 				"path": "/system/client",
// 				"name": "client",
// 				"meta": {
// 					"title": "应用管理",
// 					"icon": "el-icon-help-filled",
// 					"type": "menu"
// 				},
// 				"component": "system/client"
// 			}, {
// 				"path": "/system/log",
// 				"name": "log",
// 				"meta": {
// 					"title": "系统日志",
// 					"icon": "el-icon-warning",
// 					"type": "menu"
// 				},
// 				"component": "system/log"
// 			}]
// 		}, {
// 			"path": "/other/about",
// 			"name": "about",
// 			"meta": {
// 				"title": "关于",
// 				"icon": "el-icon-info-filled",
// 				"type": "menu"
// 			},
// 			"component": "other/about"
// 		}],
// 		"permissions": ["list.add", "list.edit", "list.delete", "user.add", "user.edit", "user.delete"]
// 	},
// 	"message": ""
// }

export default {
	menu: {
		myMenus: {
			url: `${config.API_URL}/system/menu/tree/user`,
			name: "获取我的菜单",
			get: async function(){
				return await http.get(this.url);
			}
			// get: function() {
			// 	return menuTree;
			// }
		},
		tree: {
			url: `${config.API_URL}/system/menu/tree`,
			name: "获取菜单-树",
			get: async function(){
				return await http.get(this.url);
			}
		},
		list: {
			url: `${config.API_URL}/system/menu/list`,
			name: "获取菜单",
			get: async function(){
				return await http.get(this.url);
			}
		},
		listByRole: {
			url: `${config.API_URL}/system/menu/list`,
			name: "获取菜单",
			get: async function(param){
				return await http.get(`${this.url}/${param}`);
			}
		},
		save: {
			url: `${config.API_URL}/system/menu`,
			name: "保存菜单",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system/menu`,
			name: "更新菜单",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system/menu`,
			name: "删除菜单",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	dic: {
		tree: {
			url: `${config.API_URL}/system/dic/tree`,
			name: "获取字典树",
			get: async function(){
				return await http.get(this.url);
			}
		},
		list: {
			url: `${config.API_URL}/system/dic/list`,
			name: "字典明细",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		get: {
			url: `${config.API_URL}/system/dic/get`,
			name: "获取字典数据",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	role: {
		page: {
			url: `${config.API_URL}/system/role/page`,
			name: "获取角色列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/system/role/list`,
			name: "获取角色列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/system/role`,
			name: "保存角色",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system/role`,
			name: "更新角色",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system/role`,
			name: "删除角色",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		},
		menu: {
			url: `${config.API_URL}/system/role/menu`,
			name: "更新角色-菜单",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
	},
	dept: {
		tree: {
			url: `${config.API_URL}/system/dept/tree`,
			name: "获取部门列表-树",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/system/dept`,
			name: "保存部门",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system/dept`,
			name: "更新部门",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system/dept`,
			name: "删除部门",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	user: {
		page: {
			url: `${config.API_URL}/system/user/page`,
			name: "获取用户列表-分页",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		list: {
			url: `${config.API_URL}/system/user/list`,
			name: "获取用户列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		save: {
			url: `${config.API_URL}/system/user`,
			name: "保存用户",
			post: async function(params){
				return await http.post(this.url, params);
			}
		},
		update: {
			url: `${config.API_URL}/system/user`,
			name: "更新用户",
			put: async function(params){
				return await http.put(this.url, params);
			}
		},
		delete: {
			url: `${config.API_URL}/system/user`,
			name: "删除用户",
			delete: async function(params){
				return await http.delete(`${this.url}/${params}`);
			}
		}
	},
	app: {
		list: {
			url: `${config.API_URL}/system/app/list`,
			name: "应用列表",
			get: async function(){
				return await http.get(this.url);
			}
		}
	},
	log: {
		list: {
			url: `${config.API_URL}/system/log/list`,
			name: "日志列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	table: {
		list: {
			url: `${config.API_URL}/system/table/list`,
			name: "表格列管理列表",
			get: async function(params){
				return await http.get(this.url, params);
			}
		},
		info: {
			url: `${config.API_URL}/system/table/info`,
			name: "表格列管理详情",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	},
	tasks: {
		list: {
			url: `${config.API_URL}/system/tasks/list`,
			name: "系统任务管理",
			get: async function(params){
				return await http.get(this.url, params);
			}
		}
	}
}