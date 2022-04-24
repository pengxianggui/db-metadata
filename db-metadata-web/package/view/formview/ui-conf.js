import {elementVersion} from "../../config";

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|width|表单宽度|string|-|50%|
|form_type|表单类型, 为VIEW时为详情显示|string|VIEW/ADD/UPDATE|ADD|
|conf|ElementUI(` + elementVersion + `)中<a target="_blank" href="https://element.eleme.cn/2.12/#/zh-CN/component/form#form-attributes">el-form<a></a>的原生配置项|object|-|-|
|columns|表单字段配置, 该数组中的每个对象都是单独控件的一个典型配置|array|-|-|
|buttons|表单按钮配置|object|-|-|
`;

export const formTypes = {
    view: 'VIEW',
    update: 'UPDATE',
    add: 'ADD'
}
export const getNameOfFormTypes = function (formType) {
    switch (formType.toUpperCase()) {
        case formTypes.view:
            return '查看'
        case formTypes.update:
            return '编辑'
        case formTypes.add:
            return '新增'
    }
}

export default {
    "component_name": "FormView",
    "name": "FormView",
    "label": "表单模板",
    "style": { // 表单上的样式
        "width": "60%"
    },
    "form_type": formTypes.add,
    "conf": {
        "label-width": "100px",
        "size": "medium",
        "disabled": false,
        "inline": false,
        "rules": {}
    },
    "columns": [],
    "layout": [],
    "buttons": {
        "show": true,
        "submit": {
            "show": true,
            "label": "提交",
            "conf": {
                "type": "primary"
            }
        },
        "cancel": {
            "show": true,
            "label": "取消",
            "conf": {
            }
        }
    }
}
