import Vue from 'vue'
import App from './App.vue'
import MetaElement from '../package/index' // 如果将index省略, 则会发生下面Vue.use无法正常调用的异常情况
import router from './router'
import config from '../config'
import {mockXHR} from '../mock'

if (process.env.NODE_ENV === 'development') {
    mockXHR()
}

Vue.use(MetaElement, {
    authorities: ['ADMIN'],
    axios: {
        baseURL: config.apiBaseUrl + 'meta'   // default
    },
    routeUrl: {
        baseURL: '/main'
    },
    restUrl: {},
    access: {
        adminRoleCode: 'admin', // 可以使用MetaEasyEdit功能的角色
        roles: ['admin'] // 当前用户角色
    }
});


Vue.config.productionTip = false;
Vue.prototype.NODE_ENV = process.env.NODE_ENV;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
