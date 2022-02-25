import Vue from 'vue'
import App from './App.vue'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import './svg'
import MetaElement from '../package/index' // 如果将index省略, 则会发生下面Vue.use无法正常调用的异常情况
import MyLayout from './layout'
import router from './router'
import {menus} from "./router";
import {mockXHR} from '../mock'
import axios from './axios'
import ArticleForm from "@/view/ArticleForm";
Vue.component(ArticleForm.name, ArticleForm)

if (process.env.NODE_ENV === 'development') {
    mockXHR()
}

Vue.use(ElementUI, {
    size: 'small'
});

Vue.use(MetaElement, {
    axios: axios, // axios实例(必须)
    menus: menus,
    router: router,
    routerInterceptor: { // 路由守卫
        enable: true, // 开启内置的路由守卫。开启后，由MetaElement负责值守路由，并对路由鉴权。如果关闭，你需要自行维持路由鉴权，并维持用户状态。
    },
    layout: MyLayout, // 布局组件(可选，空则默认。若为默认，则编程路由需要自行使用MetaElement中导出的MetaLayout)
    restUrl: {}, // rest请求, 用于覆盖内部rest请求url. 基本无需配置。参考【内置接口地址】
    routeUrl: {}, // 用于覆盖内置的路由地址。参考【内置路由列表】
    access: { // 访问权限配置
        root: 'ROOT' // 默认为ROOT, 如果自定义覆盖, 对于MetaEasyEdit快捷编辑是有效的, 但是平台维护路由未生效
    },
    tagView: {
        show: true,
        bgColor: '#409EFF',
        outPath: '/dashboard' // 兜底的路由
    }
});

Vue.config.productionTip = false;
Vue.prototype.$NODE_ENV = process.env.NODE_ENV;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
