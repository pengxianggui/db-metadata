import Vue from 'vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import App from './App.vue'
import router from './router'
import upperFirst from 'lodash/upperFirst'
import camelCase from 'lodash/camelCase'
import utils from '@/utils'
import axios from './axios'

Vue.use(ElementUI);

Vue.prototype.$axios = axios; // 全局注册，使用方法为:this.$axios
Vue.prototype.$merge = utils.merge;
Vue.prototype.$complieString = utils.complieVarString;

Vue.config.productionTip = false;

// 注册原子业务组件
const atomComponent = require.context(
    './components/core', // 组件目录的相对路径
    true, // 是否查询其子目录
    /\w+\.(vue)$/ // 匹配基础组件文件名的正则表达式
);
atomComponent.keys().forEach(fileName => {
    // 获取组件配置
    const componentConfig = atomComponent(fileName);

    // 获取组件的 PascalCase 命名
    const componentName = upperFirst(
        camelCase(
            // 获取和目录深度无关的文件名
            fileName
                .split('/')
                .pop()
                .replace(/\.\w+$/, '')
        )
    );
    // 全局注册组件
    Vue.component(
        componentName,
        // 如果这个组件选项是通过 `export default` 导出的，
        // 那么就会优先使用 `.default`，
        // 否则回退到使用模块的根。
        componentConfig.default || componentConfig
    )
});

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
