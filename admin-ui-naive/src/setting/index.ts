import {AppConfigState, DeviceType, LayoutMode, PageAnim, SideTheme, ThemeMode,} from '@/store/types'

export const projectName = '数据中台'
export const OAUTH2_CLIENT_ID = import.meta.env.VITE_APP_OAUTH2_CLIENT_ID
export const OAUTH2_CLIENT_SECRET = import.meta.env.VITE_APP_OAUTH2_CLIENT_SECRET
export const OAUTH2_CLIENT_SCOPE = import.meta.env.VITE_APP_OAUTH2_CLIENT_SCOPE
export const OAUTH2_CLIENT_GRANT_TYPE = import.meta.env.VITE_APP_OAUTH2_CLIENT_GRANT_TYPE

export default {
  theme: ThemeMode.LIGHT,
  sideTheme: SideTheme.WHITE,
  themeColor: '#409eff',
  layoutMode: LayoutMode.LTR,
  sideWidth: 210,
  deviceType: DeviceType.PC,
  pageAnim: PageAnim.OPACITY,
  isFixedNavBar: true,
  isCollapse: false,
  actionBar: {
    isShowSearch: true,
    isShowMessage: true,
    isShowRefresh: true,
    isShowFullScreen: true,
  },
} as AppConfigState
