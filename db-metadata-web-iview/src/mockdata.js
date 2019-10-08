
const masterMetadata = {
    "id": "1",
    "code": "meta_object_code",
    "name": "元对象",
    "table_name": "meta_object",
    "schema_name": "db_metadata",
    "primarys": "id",
    "config": {
        "default_order": "id desc",
        "size": "small",
    }
}

const masterFieldMetadata = [
    {
        "id": "1",
        "object_code": "meta_object_code",
        "is_primary": "true",
        "en": "id",
        "cn": "ID",
        "order_num": 1,
        "db_type": "varchar",
        "db_type_length": "32",
        "java_type": "String",
        "config": {
            "form_type": "INPUT",
            "editable": false, // 像这类关乎业务的配置提到config级别下
            "sortable": true,
            "showable": true,
            options: {  // 字段表单单控件options配置
                "required": false,
                // "placeholder": "",
                // "clearable": true,
                "show_label": true, // 显示内容为cn||en
                // "disabled": false,
                "readonly": true,
                // "style": {
                //     "width": "200px"
                // },
                // ... 配置内容可以直接与element ui 相同, 其它需要手动实现
            },
            search_options: {   // 搜索框单控件配置
                "searchable": true,
                "placeholder": "请输入查询关键词",
                "clearable": false,
                "show_label": true, // 显示内容为cn||en
                "size": "mini",
                // ...
            }
        }
    }, {
        "id": "2",
        "object_code": "meta_object_code",
        "is_primary": "false",
        "en": "object_code",
        "cn": "对象编码",
        "order_num": 2,
        "db_type": "varchar",
        "db_type_length": "64",
        "java_type": "String",
        "config": {
            "form_type": "INPUT",
            "editable": true, // 像这类关乎业务的配置提到config级别下
            "sortable": true,
            "showable": true,
            options: {  // 字段表单单控件options配置
                "required": true,
                "placeholder": "请输入对象编码",
                "clearable": true,
                "show_label": true, // 显示内容为cn||en
                "disabled": false,
                "readonly": false,
                "style": {
                    "width": "200px",
                    // "border": "1px solid red"
                },
                "rules": [{
                    "pattern": "/^[a-z]|[A-Z]*$/",
                    "message": "只能包含纯字母",
                    "trigger": "blur"
                }, {
                    "required": true,
                    "message": "对象编码不能为空",
                    "trigger": "blur"
                }],
                // ... 配置内容可以直接与element ui 相同, 其它需要手动实现
            },
            search_options: {   // 搜索框单控件配置
                "searchable": true,
                "placeholder": "请输入查询关键词",
                "clearable": false,
                "show_label": true, // 显示内容为cn||en
                // "size": "medium",
                "style": {
                    // "width": "100px",
                    // "border": "1px solid red"
                },
                // "rules": [{
                //     "pattern": "^[A-Za-z]+$",
                //     "message": "只能包含纯字母",
                //     "trigger": "blur"
                // }, {
                //     "required": true,
                //     "message": "对象编码不能为空",
                //     "trigger": "blur"
                // }],
                // ...
            }
        }
    }
]

const slaveMetadata = {
    "id": "1",
    "code": "meta_field_code",
    "name": "元字段",
    "table_name": "meta_field",
    "schema_name": "db_metadata",
    "primarys": "id",
    "config": {}
}

const slaveFieldMetadata = [
    {
        "id": "1",
        "object_code": "meta_field_code",
        "is_primary": "true",
        "en": "id",
        "cn": "ID",
        "order_num": 1,
        "db_type": "varchar",
        "db_type_length": "32",
        "java_type": "String",
        "config": {
            "form_type": "INPUT",
            "editable": false, // 像这类关乎业务的配置提到config级别下
            "sortable": true,
            "showable": true,
            options: {  // 字段表单单控件options配置
                "required": false,
                // "placeholder": "",
                // "clearable": true,
                "show_label": true, // 显示内容为cn||en
                // "disabled": false,
                "readonly": true,
                // "style": {
                //     "width": "200px"
                // },
                // ... 配置内容可以直接与element ui 相同, 其它需要手动实现
            },
            search_options: {   // 搜索框单控件配置
                "searchable": true,
                "placeholder": "请输入查询关键词",
                "clearable": true,
                "show_label": true, // 显示内容为cn||en
                // ...
            }
        }
    }
]

const masterData = [{
    id: "1",
    object_code: "meta_object_code"
}, {
    id: "2",
    object_code: "meta_field_code"
}, {
    id: "3",
    object_code: "test3"
}, {
    id: "4",
    object_code: "test4"
}, {
    id: "5",
    object_code: "test5"
}, {
    id: "6",
    object_code: "test6"
}, {
    id: "7",
    object_code: "test7"
}, {
    id: "8",
    object_code: "test8"
}, {
    id: "9",
    object_code: "test9"
}, {
    id: "10",
    object_code: "test10"
}, {
    id: "11",
    object_code: "test11"
}, {
    id: "12",
    object_code: "test12"
}, {
    id: "13",
    object_code: "test13"
}]

export default {
    masterMetadata,
    masterFieldMetadata,
    slaveMetadata,
    slaveFieldMetadata,
    masterData
}
