import Vue from 'vue'
import App from './App.vue'
import MetaElement from '../package/index' // 如果将index省略, 则会发生下面Vue.use无法正常调用的异常情况
import {restUrl} from "../package/index";
import router from './router'
import {mockXHR} from '../mock'

if (process.env.NODE_ENV === 'development') {
    mockXHR()
}

Vue.use(MetaElement, {
    axios: { // 内置axios配置
        baseURL: '/meta'   // default
    },
    routeUrl: { // 内置路由配置，用于覆盖内部组件的路由跳转, 通常只需要配置前缀
        baseURL: '/main' // 为内部所有跳转的路由添加前缀
    },
    restUrl: {}, // rest请求, 用于覆盖内部rest请求url. 基本无需配置
    objectCode: {}, // 对于内置模块的元对象编码, 若objectCode不一致， 可以进行配置
    featureCode: {}, // 同上, 针对需要纠正内置模块的featureCode
    access: { // 访问权限配置
        root: 'ROOT' // 默认为ROOT, 如果自定义覆盖, 对于MetaEasyEdit快捷编辑是有效的, 但是平台维护路由未生效
    }
});

// 异步动态路由装载尝试
// import exchange from '@/../package/route/exchange'
//
// // TODO 这部分装配考虑放到MetaElement中完成, 对业务系统透明
// Vue.prototype.$axios.get(restUrl.ROUTE_DATA).then(resp => {
//     router.addRoutes(exchange(resp.data))
// })

Vue.config.productionTip = false;
Vue.prototype.$NODE_ENV = process.env.NODE_ENV;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
