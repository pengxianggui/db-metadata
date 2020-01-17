import Vue from 'vue'
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

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
