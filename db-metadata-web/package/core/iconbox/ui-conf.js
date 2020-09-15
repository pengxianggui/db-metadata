export const ConfDesc = `
    # 控件支持三种方式应用图标：
    1.font-icon: 只支持内嵌的element字体图标
    2.svg图标: 除了内置的，可以支持扩展，自定义添加新图标
    3.外置链接图标地址
    > 但必须与svg-icon组件进行配套使用
    
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
`;

export default {
    "component_name": "IconBox",
    "name": "IconBox",
    "label": "图标框"
}