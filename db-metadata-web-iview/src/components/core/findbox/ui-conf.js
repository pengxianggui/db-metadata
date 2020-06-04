export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
    |conf|支持{'clearable': true, 'placeholder': ''}|object|object|-|
`;

export default {
    "component_name": "FindBox",
    "name": "FindBox",
    "label": "查找框",
    "data_url": "/meta/fields/{objectCode}", // 这个data_url响应的是FindPanel的meta, 也可以是TableList的meta
    "inline": false,
    "conf": {
        "clearable": true,
        "placeholder": "戳我展开搜索面板.."
    }
}