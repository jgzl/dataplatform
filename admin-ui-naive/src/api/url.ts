export const test = '/test'

export const login = '/auth/oauth2/token?grant_type=password&scope=server'

export const updateUserInfo = '/updateUser'

export const addUserInfo = '/addUser'

export const getMenuListByRoleId = '/getMenusByRoleId'

export const getAllMenuByRoleId = '/getAllMenuByRoleId'

export const deleteUserById = '/deleteUserById'

export const getDepartmentList = '/getDepartmentList'

export const addDepartment = '/addDepartment'

export const getRoleList = '/getRoleList'

export const getMenuList = '/getMenuList'

export const getParentMenuList = '/getParentMenuList'

export const getTableList = '/getTableList'

export const getCardList = '/getCardList'

export const getCommentList = '/getCommentList'

// 系统管理
// 系统管理-菜单树形根据用户
export const systemMenuTreeByUser = '/gateway-admin/system/menu/tree/user'
// 系统管理-菜单树形根据角色
export const systemMenuTreeByRole = '/gateway-admin/system/menu/tree/role'
// 系统管理-菜单树形
export const systemMenuTree = '/gateway-admin/system/menu/tree'
// 系统管理-菜单列表根据角色
export const systemMenuList = '/gateway-admin/system/menu/list'
// 系统管理-菜单增删改
export const systemMenu = '/gateway-admin/system/menu'
// 系统管理-部门树形
export const systemDeptTree = '/gateway-admin/system/dept/tree'
// 系统管理-部门增删改
export const systemDept = '/gateway-admin/system/dept'
// 系统管理-角色增删改
export const systemRole = '/gateway-admin/system/role'
// 系统管理-根据角色更新菜单
export const systemRoleMenu = '/gateway-admin/system/role/menu'
// 系统管理-角色列表查询
export const systemRoleList = '/gateway-admin/system/role/list'
// 系统管理-角色分页列表查询
export const systemRolePage = '/gateway-admin/system/role/page'
// 系统管理-用户列表查询
export const systemUserList = '/gateway-admin/system/user/list'
// 系统管理-用户分页列表查询
export const systemUserPage = '/gateway-admin/system/user/page'
// 系统管理-用户增删改
export const systemUser = '/gateway-admin/system/user'
// 系统管理-用户禁用启用
export const systemUserForLockFlag = '/gateway-admin/system/user/lockFlag'

// 网关管理
// 网关管理-日志搜索
export const gatewayLogSearch = '/gateway-admin/gateway/log/search'
// 网关管理-日志增删改
export const gatewayLogs = '/gateway-admin/gateway/log'
// 网关管理-路由增删改查
export const gatewayRoute = '/gateway-admin/gateway/route'
// 网关管理-分页查询
export const gatewayRoutePage = '/gateway-admin/gateway/route/page'
// 网关管理-访问增删改查
export const gatewayAccess = '/gateway-admin/gateway/access'
// 网关管理-分页查询
export const gatewayAccessPage = '/gateway-admin/gateway/access/page'
// 网关管理-访问禁用启用
export const gatewayAccessStatus = '/gateway-admin/gateway/access/status'

declare module '@vue/runtime-core' {
  interface ComponentCustomProperties {
    $urlPath: Record<string, string>
  }
}
