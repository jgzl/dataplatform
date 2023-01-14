import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/display.css'
import scui from './scui'
import i18n from './locales'
import router from './router'
import store from './store'
import {createApp} from 'vue'
import App from './App.vue'

import axios from 'axios'
import * as echarts from 'echarts'

//引用全局变量文件
import GLOBAL_VAR from './api/global_variable.js'
import GLOBAL_FUN from './api/global_function.js'

const app = createApp(App);

app.use(store);
app.use(router);
app.use(ElementPlus);
app.use(i18n);
app.use(scui);

//绑定到vue属性
app.config.globalProperties.GLOBAL_VAR = GLOBAL_VAR
app.config.globalProperties.GLOBAL_FUN = GLOBAL_FUN
app.config.globalProperties.$ajax = axios
// Vue.prototype.$echarts = require('echarts')
app.config.globalProperties.$echarts = echarts

//挂载app
app.mount('#app');
