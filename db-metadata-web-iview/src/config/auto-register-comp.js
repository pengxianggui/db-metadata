import upperFirst from "lodash/upperFirst";
import camelCase from "lodash/camelCase";
import Vue from 'vue'

const atomComponent = require.context(
    '../components/core', // 组件目录的相对路径
    true, // 是否查询其子目录
    /\w+\.(vue)$/ // 匹配基础组件文件名的正则表达式
);
atomComponent.keys().forEach(fileName => {
    // 获取组件配置
    const componentConfig = atomComponent(fileName);

    // 获取组件的 PascalCase命名(eg: HelloWorld)
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