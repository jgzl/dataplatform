import {getMenuList} from '@/api/url'
import Mock from 'mockjs'

export const adminRoutes = [
  {
    component: 'index',
    name: 'Dashborad',
    routeName: 'dashborad',
    icon: 'icon-dashboard',
    parentPath: '',
    children: [
      {
        parentPath: '/index',
        component: 'index/home',
        name: '主控台',
        routeName: 'home',
      },
      {
        parentPath: '/index',
        component: 'index/work-place',
        name: '工作台',
        routeName: 'workPlace',
        isRootPath: true,
      },
    ],
  },
  {
    component: 'system',
    name: '系统管理',
    iconPrefix: 'iconfont',
    icon: 'setting',
    parentPath: '',
    children: [
      {
        parentPath: '/system',
        component: 'system/department',
        name: '部门管理',
        badge: 'new',
        localFilePath: 'system/local-path/department',
        routeName: 'my-department',
      },
      {
        parentPath: '/system',
        component: 'system/user',
        name: '用户管理',
        badge: 'dot',
      },
      {
        parentPath: '/system',
        component: 'system/role',
        name: '角色管理',
        badge: '12',
      },
      {
        parentPath: '/system',
        component: 'system/menu',
        name: '菜单管理',
      },
    ],
  },
  {
    component: 'list',
    name: '列表页面',
    iconPrefix: 'iconfont',
    icon: 'detail',
    parentPath: '',
    children: [
      {
        parentPath: '/list',
        component: 'list/table-with-search',
        name: '表格搜索',
      },
      {
        parentPath: '/list',
        component: 'list/table-custom',
        name: '自定义表格',
      },
      {
        parentPath: '/list',
        component: 'list/list',
        name: '普通列表',
      },
      {
        parentPath: '/list',
        component: 'list/card-list',
        name: '卡片列表',
      },
    ],
  },
  {
    component: 'form',
    name: '表单页面',
    badge: 'dot',
    iconPrefix: 'iconfont',
    icon: 'file-text',
    parentPath: '',
    children: [
      {
        parentPath: '/form',
        component: 'form/base-form-view',
        name: '动态表单',
        cacheable: true,
      },
      {
        parentPath: '/form',
        component: 'form/advance-form',
        name: '高级表单',
        cacheable: true,
      },
      {
        parentPath: '/form',
        component: 'form/step-form',
        name: '分步表单',
      },
    ],
  },
  {
    component: 'other',
    name: '功能/组件',
    iconPrefix: 'iconfont',
    icon: 'appstore',
    parentPath: '',
    children: [
      {
        parentPath: '/other',
        component: 'other/chart',
        name: '图表',
        children: [
          {
            parentPath: '/other/chart',
            component: 'other/chart/icon',
            name: '图标',
          },
          {
            parentPath: '/other/chart',
            component: 'other/chart/echarts',
            name: 'echarts',
          },
          {
            parentPath: '/other/chart',
            component: 'other/chart/icon-selector',
            name: '图标选择器',
          },
        ],
      },
      {
        parentPath: '/other',
        component: 'other/print',
        name: '打印',
      },
      {
        parentPath: '/other',
        component: 'other/badge',
        name: '消息提示',
      },
      {
        parentPath: '/other',
        component: 'other/clipboard',
        name: '剪贴板',
      },
      {
        parentPath: '/other',
        component: 'http://www.vueadminwork.com',
        name: '外链（官网）',
      },
      {
        parentPath: '/other',
        component: 'other/qrcode',
        name: '二维码',
      },
      {
        parentPath: '/other',
        component: 'other/css-animation',
        name: 'CSS动画',
      },
      {
        parentPath: '/other',
        component: 'other/player',
        name: '播放器',
      },
      {
        parentPath: '/other',
        component: 'other/big-preview',
        name: '大图预览',
      },
      {
        parentPath: '/other',
        component: 'other/city-selector',
        name: '省市区选择器',
      },
    ],
  },
  {
    component: 'result',
    name: '结果页面',
    iconPrefix: 'iconfont',
    icon: 'file-unknown',
    parentPath: '',
    children: [
      {
        parentPath: '/result',
        component: 'result/success',
        name: '成功页面',
      },
      {
        parentPath: '/result',
        component: 'result/fail',
        name: '失败页面',
      },
    ],
  },
  {
    component: 'editor',
    name: '编辑器',
    badge: '12',
    iconPrefix: 'iconfont',
    icon: 'edit',
    parentPath: '',
    children: [
      {
        parentPath: '/editor',
        component: 'editor/rich-text',
        name: '富文本',
      },
      {
        parentPath: '/editor',
        component: 'editor/markdown',
        name: 'markdown',
      },
    ],
  },
  {
    component: 'draggable',
    name: '拖拽',
    iconPrefix: 'iconfont',
    icon: 'interation',
    parentPath: '',
    children: [
      // {
      //   parentPath: '/draggable',
      //   component: 'draggable/dialog-draggable',
      //   name: '拖拽对话框',
      // },
      {
        parentPath: '/draggable',
        component: 'draggable/card-draggable',
        name: '卡片拖拽',
        cacheable: true,
      },
    ],
  },
  {
    component: 'next',
    name: '多级菜单',
    iconPrefix: 'iconfont',
    icon: 'Partition',
    parentPath: '',
    children: [
      {
        parentPath: '/next',
        component: 'next/menu1',
        name: 'menu-1',
        cacheable: true,
      },
      {
        parentPath: '/next',
        component: 'next/menu2',
        name: 'menu-2',
        children: [
          {
            parentPath: '/next/menu2',
            component: 'next/menu2/menu-2-1',
            name: 'menu-2-1',
            children: [
              {
                parentPath: '/next/menu2/menu-2-1',
                component: 'next/menu2/menu-2-1/menu-2-1-1',
                name: 'menu-2-1-1',
                cacheable: true,
              },
              {
                parentPath: '/next/menu2/menu-2-1',
                component: 'next/menu2/menu-2-1/menu-2-1-2',
                name: 'menu-2-1-2',
              },
            ],
          },
          {
            parentPath: '/next/menu2',
            component: 'next/menu2/menu-2-2',
            name: 'menu-2-2',
            cacheable: true,
          },
        ],
      },
    ],
  },
  {
    component: 'map',
    name: '地图',
    iconPrefix: 'iconfont',
    icon: 'location',
    children: [
      {
        parentPath: '/map',
        component: 'map/gaode',
        name: '高德地图',
      },
      {
        parentPath: '/map',
        component: 'map/baidu',
        name: '百度地图',
      },
    ],
  },
  {
    component: 'project',
    name: '项目信息',
    iconPrefix: 'iconfont',
    icon: 'detail',
    isSingle: true,
    children: [
      {
        parentPath: '/project',
        component: 'project/infomation',
        name: '项目依赖',
      },
    ],
  },
]
export const editorRoutes = [
  {
    component: 'list',
    name: '列表页面',
    iconPrefix: 'iconfont',
    icon: 'detail',
    parentPath: '',
    children: [
      {
        parentPath: '/list',
        component: 'list/table-with-search',
        name: '表格搜索',
      },
      {
        parentPath: '/list',
        component: 'list/table-custom',
        name: '自定义表格',
      },
      {
        parentPath: '/list',
        component: 'list/list',
        name: '普通列表',
      },
      {
        parentPath: '/list',
        component: 'list/card-list',
        name: '卡片列表',
      },
    ],
  },
  {
    component: 'form',
    name: '表单页面',
    badge: 'dot',
    iconPrefix: 'iconfont',
    icon: 'file-text',
    parentPath: '',
    children: [
      {
        parentPath: '/form',
        component: 'form/base-form-view',
        name: '动态表单',
        cacheable: true,
      },
      {
        parentPath: '/form',
        component: 'form/advance-form',
        name: '高级表单',
        cacheable: true,
      },
      {
        parentPath: '/form',
        component: 'form/step-form',
        name: '分步表单',
      },
    ],
  },
  {
    component: 'other',
    name: '功能/组件',
    iconPrefix: 'iconfont',
    icon: 'appstore',
    parentPath: '',
    children: [
      {
        parentPath: '/other',
        component: 'other/chart',
        name: '图表',
        children: [
          {
            parentPath: '/other/chart',
            component: 'other/chart/icon',
            name: '图标',
          },
          {
            parentPath: '/other/chart',
            component: 'other/chart/echarts',
            name: 'echarts',
          },
          {
            parentPath: '/other/chart',
            component: 'other/chart/icon-selector',
            name: '图标选择器',
          },
        ],
      },
      {
        parentPath: '/other',
        component: 'other/print',
        name: '打印',
      },
      {
        parentPath: '/other',
        component: 'other/badge',
        name: '消息提示',
      },
      {
        parentPath: '/other',
        component: 'other/clipboard',
        name: '剪贴板',
      },
      {
        parentPath: '/other',
        component: 'http://www.baidu.com',
        name: '外链',
      },
      {
        parentPath: '/other',
        component: 'other/qrcode',
        name: '二维码',
      },
      {
        parentPath: '/other',
        component: 'other/css-animation',
        name: 'CSS动画',
      },
      {
        parentPath: '/other',
        component: 'other/flow',
        name: '流程图',
      },
      {
        parentPath: '/other',
        component: 'other/player',
        name: '播放器',
      },
      {
        parentPath: '/other',
        component: 'other/big-preview',
        name: '大图预览',
      },
      {
        parentPath: '/other',
        component: 'other/city-selector',
        name: '省市区选择器',
      },
    ],
  },
  {
    component: 'result',
    name: '结果页面',
    iconPrefix: 'iconfont',
    icon: 'file-unknown',
    parentPath: '',
    children: [
      {
        parentPath: '/result',
        component: 'result/success',
        name: '成功页面',
      },
      {
        parentPath: '/result',
        component: 'result/fail',
        name: '失败页面',
      },
    ],
  },
  {
    component: 'editor',
    name: '编辑器',
    badge: '12',
    iconPrefix: 'iconfont',
    icon: 'edit',
    parentPath: '',
    children: [
      {
        parentPath: '/editor',
        component: 'editor/rich-text',
        name: '富文本',
      },
      {
        parentPath: '/editor',
        component: 'editor/markdown',
        name: 'markdown',
      },
    ],
  },
  {
    component: 'next',
    name: '多级菜单',
    iconPrefix: 'iconfont',
    icon: 'Partition',
    parentPath: '',
    children: [
      {
        parentPath: '/next',
        component: 'next/menu1',
        name: 'menu-1',
        cacheable: true,
      },
      {
        parentPath: '/next',
        component: 'next/menu2',
        name: 'menu-2',
        children: [
          {
            parentPath: '/next/menu2',
            component: 'next/menu2/menu-2-1',
            name: 'menu-2-1',
            children: [
              {
                parentPath: '/next/menu2/menu-2-1',
                component: 'next/menu2/menu-2-1/menu-2-1-1',
                name: 'menu-2-1-1',
                cacheable: true,
              },
              {
                parentPath: '/next/menu2/menu-2-1',
                component: 'next/menu2/menu-2-1/menu-2-1-2',
                name: 'menu-2-1-2',
              },
            ],
          },
          {
            parentPath: '/next/menu2',
            component: 'next/menu2/menu-2-2',
            name: 'menu-2-2',
            cacheable: true,
          },
        ],
      },
    ],
  },
  {
    component: 'map',
    name: '地图',
    iconPrefix: 'iconfont',
    icon: 'location',
    children: [
      {
        parentPath: '/map',
        component: 'map/gaode',
        name: '高德地图',
      },
      {
        parentPath: '/map',
        component: 'map/baidu',
        name: '百度地图',
      },
    ],
  },
  {
    component: 'project',
    name: '项目信息',
    iconPrefix: 'iconfont',
    icon: 'detail',
    isSingle: true,
    children: [
      {
        parentPath: '/project',
        component: 'project/infomation',
        name: '项目依赖',
      },
    ],
  },
]

Mock.mock(RegExp(getMenuList), 'post', function () {
  return Mock.mock({ code: 200, data: adminRoutes, msg: '获取菜单列表成功' })
})
