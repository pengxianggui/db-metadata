import {elementVersion} from "../../config";

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|seats|文件含义组成的字符数组, 一个坑一个上传按钮, 对应一个文件。例如:['营业执照','身份证正面','身份证反面']， 需要特别注意, 配置此字段后conf.limit无效, 每个上传按钮只能输入一个图片|array of string|-|[]|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/upload#attribute">el-upload</a>的原生配置项|object|-|-|
`;

export default {
    "component_name": "ImgBox",
    "name": "ImgBox",
    "label": "图片上传框",
    'sort': 0,
    "inline": false,
    "seats": [''],
    "conf": {
        "action": "/file/upload?objectCode={objectCode}&fieldCode={fieldCode}",
        "drag": false,
        "list-type": "picture-card",
        "auto-upload": true,
        "show-file-list": true,
        "limit": 1,
        "multiple": false,  // 暂时单选
    },
    "explain": "" // 字段解释
}
