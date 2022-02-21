export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|


# 控件支持三种方式应用图标：
1.font-icon: 只支持内嵌的element字体图标
2.svg图标: 除了内置的，可以支持扩展，自定义添加新svg图标
<code>
    const req = require.context(svgDir, true, /\\.svg$/)
    const requireAll = requireContext => requireContext.keys().map(requireContext)
    requireAll(req)
    
    使用svg-sprite-loader处理:
    
    config.module
            .rule('svg')
            .exclude.add(path.join(__dirname, 'src/svg'))
            .end()
        config.module
            .rule('icons')
            .test(/\\.svg$/)
            .include.add(path.join(__dirname, 'src/svg'))
            .end()
            .use('svg-sprite-loader')
            .loader('svg-sprite-loader')
            .options({
                symbolId: 'me-icon-[name]'
            })
            .end()
</code>
3.外置链接图标地址
> 但必须与svg-icon组件进行配套使用

`;

export default {
    "component_name": "IconBox",
    "name": "IconBox",
    "label": "图标框",
    'sort': 0,
    "explain": "" // 字段解释
}
