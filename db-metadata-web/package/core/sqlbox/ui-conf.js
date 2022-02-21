export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|check|是否显示sql校验按钮|boolean|true/false|true|
|check_url|自定义sql校验逻辑时,提供服务端接口,用以校验sql正确性|string|-|/check/sql?sql={sql}|
|theme|主题|string|default|default/ambiance|
|lineNumbers|是否显示行号|boolean|true/false|true|
`;

export default {
    "component_name": "SqlBox",
    "name": "SqlBox",
    "label": "SQL输入框",
    'sort': 0,
    "check": false,
    "check_url": "/check/sql?sql={sql}",
    "theme": "default", // default、ambiance
    "line_number": true,
    "mode": 'text/x-mysql',
    "conf": {},
    "explain": "" // 字段解释
}
