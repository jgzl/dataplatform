import {LAYOUT} from '@/store/keys'

export const asyncRoutes = [
  {
    path: '/home',
    component: LAYOUT,
    name: '首页',
    meta: {
      title: '首页',
      iconPrefix: 'iconfont',
      icon: 'dashboard',
    },
    children: [
      {
        path: '/dashboard',
        name: 'Home',
        component: () => import('@/views/home/main.vue'),
        meta: {
          title: '工作台',
          affix: true,
          cacheable: true,
          iconPrefix: 'iconfont',
          icon: 'menu',
        },
      },
      {
        path: '/userCenter',
        name: '用户中心',
        component: () => import('@/views/home/work-place.vue'),
        meta: {
          title: '用户中心',
          affix: true,
          iconPrefix: 'iconfont',
          icon: 'menu',
        },
      },
    ],
  },
]
