/**
 * 这里的 defaultRoutes 是为了在一开始对接项目的时候，后端人员还没有准备好菜单接口，导致前端开发者不能进入主页面。
 * 所以这里返回默认的菜单数据，同时也向大家说明菜单数据的数据结构。后端的菜单接口一定要按这个格式去返回json数据，否则会解析菜单失败
 */
export default [
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
      },
    ],
  },
  {
    component: 'system',
    name: '系统管理',
    icon: 'icon-settings',
    parentPath: '',
    routeName: 'system',
    children: [
      {
        parentPath: '/system',
        component: 'system/department',
        name: '部门管理',
        routeName: 'department',
        localFilePath: '/system/local-path/department',
      },
      {
        parentPath: '/system',
        component: 'system/user',
        name: '用户管理',
        routeName: 'user',
        isRootPath: true,
      },
      {
        parentPath: '/system',
        component: 'system/role',
        name: '角色管理',
      },
      {
        parentPath: '/system',
        component: 'system/menu',
        name: '菜单管理',
      },
    ],
  },
]
