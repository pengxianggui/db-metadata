export const ConfDesc = `
> 此组件需配合db-metadata后端接口

|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|data_url|获取弹窗数据的url|String|-|/table/list?objectCode={objectCode}|
|conf|支持{'clearable': true, 'placeholder': ''}|object|object|-|
`;

export const panelMeta = {
    "component_name": "FindPanel",
    "name": "FindPanel",
    "label": "查找面板",
    "data_url": "/table/list?objectCode={objectCode}",
    "columns": []
};

export default {
    "component_name": "FindBox",
    "name": "FindBox",
    "label": "查找框",
    "data_url": "/find/meta?objectCode={objectCode}&fieldCode={fieldCode}",
    "conf": {
        "clearable": true,
        "placeholder": "戳我展开搜索面板.."
    },
    "explain": ""
}
