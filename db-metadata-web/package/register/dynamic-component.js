import Vue from 'vue'

/**
 * https://www.cnblogs.com/sefaultment/p/10354038.html
 * https://www.jianshu.com/p/bf5b1747bcfe
 */

export function autoLoadingGlobalComponent() {
    const requireComponent = require.context(
        // 其组件目录的相对路径
        '@/components/cover',
        // 是否查询其子目录
        false,
        // 匹配vue后缀文件名的文件
        /\.vue$/
    )
    // 遍历获取到的文件名，依次进行全局注册
    requireComponent.keys().forEach(fileName => {
        // 获取组件配置(实例)
        const componentConfig = requireComponent(fileName)
        // 获取组件的 PascalCase 命名(eg: MYHeader)
        // const componentName = _.upperFirst(
        //     // 获取驼峰式命名
        //     _.camelCase(
        //         // 剥去文件名开头的 `./` 和结尾的扩展名 eg: ./food-header.vue -> foodHeader
        //         fileName.replace(/^\.\/(.*)\.\w+$/, '$1')
        //     )
        // )
        const componentName = fileName.replace(/^\.\//, '').replace(/\.vue$/, '');
        // 全局注册组件
        Vue.component(
            componentName.replace(/\//, '-'),
            // 如果这个组件选项是通过 `export default` 导出的，
            // 那么就会优先使用 `.default`，
            // 否则回退到使用模块的根。
            componentConfig.default || componentConfig
        )
    })
}