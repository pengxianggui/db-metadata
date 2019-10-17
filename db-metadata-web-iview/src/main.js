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

Vue.prototype.$axios = axios // 全局注册，使用方法为:this.$axios

Vue.config.productionTip = false;

// 全局混入, 谨慎扩展
Vue.mixin({
    created() {
    },
    methods: {
        $merge: utils.merge
    }
});

// 注册原子业务组件
const requireComponent = require.context(
    // 组件目录的相对路径
    './components/atom',
    // 是否查询其子目录
    false,
    // 匹配基础组件文件名的正则表达式
    /\w+\.(vue|js)$/
)
requireComponent.keys().forEach(fileName => {
    // 获取组件配置
    const componentConfig = requireComponent(fileName)

    // 获取组件的 PascalCase 命名
    const componentName = upperFirst(
        camelCase(
            // 获取和目录深度无关的文件名
            fileName
                .split('/')
                .pop()
                .replace(/\.\w+$/, '')
        )
    )
    // 全局注册组件
    Vue.component(
        componentName,
        // 如果这个组件选项是通过 `export default` 导出的，
        // 那么就会优先使用 `.default`，
        // 否则回退到使用模块的根。
        componentConfig.default || componentConfig
    )
})

new Vue({
    router,
    render: h => h(App),
}).$mount('#app');
