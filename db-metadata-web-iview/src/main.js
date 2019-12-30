import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import  '@/mock/api'
import App from './App.vue'
import router from './router'

import './config/auto-register-comp';   // 全局组件注册
import './config/auto-register-fn';
import RegisterGlobalFn from  './config/auto-register-fn';     // 全局方法注册

Vue.use(RegisterGlobalFn);
Vue.use(ElementUI);

Vue.config.productionTip = false;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
