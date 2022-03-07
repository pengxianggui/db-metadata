import Vue from 'vue'
import App from './App.vue'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import MetaElement from '../package/index' // 如果将index省略, 则会发生下面Vue.use无法正常调用的异常情况
import router from './router'
import {menus} from "./router";
import axios from './axios'

Vue.use(ElementUI, {
    size: 'small'
});

Vue.use(MetaElement, {
    // 必要的配置
    axios: axios, // axios实例(必须)
    router: router, // 路由实例(必须)

    // 以下为非必要配置
    menus: menus, // 编程菜单
    routerInterceptor: { // 路由守卫
        enable: true, // 开启内置的路由守卫。开启后，由MetaElement负责值守路由，并对路由鉴权。如果关闭，你需要自行维持路由鉴权，并维持用户状态。
    },
    // layout: MyLayout, // 布局组件(可选，空则默认。若为默认，则编程路由需要自行使用MetaElement中导出的MetaLayout)
    restUrl: {}, // rest请求, 用于覆盖内部rest请求url. 基本无需配置。参考【内置接口地址】
    routeUrl: {}, // 用于覆盖内置的路由地址。参考【内置路由列表】
    access: { // 访问权限配置
        root: '0' // ROOT用户的id, 默认就是0。
    }
});

Vue.config.productionTip = false;
Vue.prototype.$NODE_ENV = process.env.NODE_ENV;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
