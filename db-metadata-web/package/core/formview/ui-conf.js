import {elementVersion} from "../../config";

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/form#form-attributes">el-form<a></a>的原生配置项|object|-|-|
|columns|表单字段配置, 该数组中的每个对象都是单独控件的一个典型配置|array|-|-|
|buttons|表单按钮配置|object|-|-|
`;

export default {
    "component_name": "FormView",
    "name": "FormView",
    "label": "表单模板",
    // "action": "/form/doAdd/{objectCode}", // form action (url), pxg_todo 屏蔽, FormBuilder实例配置时,会导致此属性和值配入库中, 但是此值需要区分三种表单环境
    "conf": {
        "label-width": '100px',
        "size": 'medium', // medium|small|mini
        "disabled": false,
        "inline": false,
        "rules": {
            // eg:
            // "id": [{required: true, message: "必填字段", trigger: "blur"}],
            // ...
        },
        // ...
    },
    "columns": [],
    "buttons": {
        "show": true,
        "submit": {
            "label": "提交",
            "conf": {
                // ... support conf of el-button
                "type": "primary"
            }
        },
        "cancel": {
            "label": '取消',
            "conf": {
                // ... support conf of el-button
            }
        }
    }
}
