import Vue from 'vue'
import App from './App.vue'
import router from './router'
import config from '../config'
import {mockXHR} from '../mock'

import MetaElement from './components'

if (process.env.NODE_ENV === 'development') {
    mockXHR()
}

Vue.use(MetaElement, {
    authorities: ['ADMIN'],
    axios: {
        baseURL: config.apiBaseUrl + 'meta'   // default
    },
    routeUrl: {
        // R_GOBAL_CONF_ADD: '/main/global-conf'
        // TODO 将路由配置全部提供覆盖机制
    },
    restUrl: {
        // TODO rest接口提供覆盖机制
    }
});

Vue.config.productionTip = false;
Vue.prototype.NODE_ENV = process.env.NODE_ENV;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
