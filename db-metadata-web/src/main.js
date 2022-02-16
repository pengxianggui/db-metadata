import Vue from 'vue'
import App from './App.vue'
import './svg'
import MetaElement from '../package/index' // 如果将index省略, 则会发生下面Vue.use无法正常调用的异常情况
import Layout from './layout'
import router from './router'
import {mockXHR} from '../mock'
import axios from './axios'
import ArticleForm from "@/view/ArticleForm";
Vue.component(ArticleForm.name, ArticleForm)

if (process.env.NODE_ENV === 'development') {
    mockXHR()
}

Vue.use(MetaElement, {
    axios: axios, // axios实例(必须)
    router: router, // 路由实例(必须)
    layout: Layout, // 布局组件(可选，空则默认)
    restUrl: {}, // rest请求, 用于覆盖内部rest请求url. 基本无需配置
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
