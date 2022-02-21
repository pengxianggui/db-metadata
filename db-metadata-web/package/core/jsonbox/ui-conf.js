export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|default_value|默认值|object|-|{}|
|conf|Json框的配置:{'mode':'code', 'modes': ['code','tree','text','view','form']}|object|-|-|
`;

export default {
    "component_name": "JsonBox",
    "name": "JsonBox",
    "label": "Json框",
    'sort': 0,
    "inline": false,
    "default_value": {},
    "conf": {
        "mode": "code",
        "modes": [
            "code",
            "tree",
            "text",
            "view",
            "form"
        ]
    },
    "explain": "" // 字段解释
}
