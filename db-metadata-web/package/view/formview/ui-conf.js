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
