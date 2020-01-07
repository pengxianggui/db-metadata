import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/mock/api'
import App from './App.vue'
import router from './router'
import config from '../config'

import MetaElement from './components'

Vue.use(MetaElement, {
    authorities: ['ADMIN'],
    axios: {
        baseURL: config.apiBaseUrl + 'meta'   // default
    }
});

Vue.use(ElementUI);

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
