import {elementVersion} from "../../config";

export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |conf|ElementUI(` + elementVersion + `)中el-checkbox的原生配置项|object|-|-|
    |inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
`;

export default {
    'component_name': 'BoolBox',
    'name': 'BoolBox',
    'label': '布尔框',
    'default_value': false,
    'inline': true,
    'conf': {}
}
