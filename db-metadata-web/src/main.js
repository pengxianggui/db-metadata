import Vue from 'vue'
import App from './App.vue'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import MetaElement from '../package/index' // 如果将index省略, 则会发生下面Vue.use无法正常调用的异常情况
import router from './router'
import metaElementConfig from "@/metaElementConfig";

Vue.use(ElementUI, {
    size: 'small'
});

Vue.use(MetaElement, metaElementConfig);

Vue.config.productionTip = false;
Vue.prototype.$NODE_ENV = process.env.NODE_ENV;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
