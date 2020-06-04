export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |conf|ui配置属性|object|-|-|
`;

export default {
    "component_name": "DialogBox",
    "name": "DialogBox",
    "label": "弹出框",
    "conf": {
        "title": "标题",
        "fullscreen": false,
        "width": "50%",
        "modal": true,
        "lock-scroll": true,
        "show-close": true,
        "center": false,
        "destroy-on-close": true
    }
}