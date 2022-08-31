import role from './role'

role.install = function (Vue) {
  Vue.directive('role', role)
}

export default role
