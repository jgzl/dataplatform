import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "./utils/router";
import ElementPlus,{ ElMessage } from "element-plus";
import "element-plus/dist/index.css";
import "dayjs/locale/zh-cn";
import zhCn from "element-plus/es/locale/lang/zh-cn";
import "@/icons/iconfont/iconfont.css";
import "@/icons/iconfont/iconfont.js";
import "@/styles/main.css";
import LayoutStore from "@/layouts";
import http from "@/api/http";
import { registerComponents } from "./components";
import dragger from "./directive/draggable/index";
import * as Icons from "@element-plus/icons";
import pinia from "./store/pinia";

import role from './directive/role/index';
import permission from './directive/permission/index';

import "./setting";

const app = createApp(App);
Object.keys(Icons).forEach((it) => {
  app.component(it, (Icons as any)[it]);
});
registerComponents(app);
app.use(LayoutStore, {
  state: {
    layoutMode: "ltr",
  },
  actions: {
    onPersonalCenter() {
      router.push({ path: "/personal", query: { uid: 1 } });
    },
    onLogout() {
      http.get({url:"/user/logout"}).then(()=>{
        router.replace({ path: "/login", query: { redirect: "/" } }).then(() => {
          window.location.reload();
        });
      }).catch(reason => {
        ElMessage.error("登出异常")
      });
    },
  },
});
app.use(pinia).use(router);
app.use(ElementPlus, {
  locale: zhCn,
});
app.use(http);
app.use(dragger);
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignores
app.use(role);
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
app.use(permission);
app.mount("#app");