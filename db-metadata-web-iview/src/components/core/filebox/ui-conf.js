import {elementVersion} from "@/config";

export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
    |conf|ElementUI(` + elementVersion + `)中el-upload的原生配置项|object|-|-|
`;

export default {
    "component_name": "FileBox",
    "name": "FileBox",
    "label": "文件上传框",
    "inline": false,
    "conf": {
        "action": "/file/upload?objectCode={objectCode}&fieldCode={fieldCode}",
        "tip": "上传文件限制不超过2M",
        "auto-upload": true,
        "show-file-list": true,
        "limit": 1,
        "multiple": false,  // 暂时单选
    }
}
