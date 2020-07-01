import Vue from 'vue'

const requireComponent = require.context('@/components/cover', true, /\.vue$/);
const dynamicRoutes = requireComponent.keys().map(fileName => {
    // 获取组件配置
    const componentConfig = requireComponent(fileName);
    // 剥去文件名开头的 `./` 和`.vue`结尾的扩展名
    const componentName = fileName.replace(/^\.\//, '').replace(/\.vue$/, '');
    // 全局注册组件
    const component = Vue.component(
        componentName.replace(/\//, '-'),
        // 如果这个组件选项是通过 `export default` 导出的，那么就会优先使用 `.default`，否则回退到使用模块的根。
        componentConfig.default || componentConfig
    );
    return {
        path: '/main/cover/' + componentName,
        name: componentName.replace(/\//, '-'),
        component
    }
});
export default dynamicRoutes;
