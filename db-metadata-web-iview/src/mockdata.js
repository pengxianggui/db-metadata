import DICT from "./constant/dict";

// ##################### MasterSlaveTableTmpl

// 主表搜索meta
const masterSearchBarMetadata = [
    {
        "id": "1",
        "object_code": "meta_object_code",
        "en": "id",
        "cn": "ID",
        "order_num": 1,
        "db_type": "varchar",
        "db_type_length": "32",
        "java_type": "String",
        "component_name": "TextBox",
        "ui_config": {
            "placeholder": "请输入查询关键词",
            "clearable": false,
            "show_label": true, // 显示内容为cn||en
            "size": "small",
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
        "component_name": "TextBox",
        "ui_config": {
            "placeholder": "请输入查询关键词",
            "clearable": false,
            "show_label": true, // 显示内容为cn||en
            "size": "small", // mini, small, medium
            "rules": [] // 预置几种常见的rule，提供正则输入。 默认内容：msg: 格式不匹配; trigger: blur，预留pattern给用户定制。
        },
        // "rules": [{
        //     "pattern": /\w+$/,
        //     "message": "只能包含字母数字和下划线",
        //     "trigger": "blur"
        // }, {
        //     "required": true,
        //     "message": "对象编码不能为空",
        //     "trigger": "blur"
        // }],
    }
]

// 主表元对象
const masterMetadata = {
    "id": "1",
    "code": "meta_object_code",
    "name": "元对象",
    "table_name": "meta_object",
    "schema_name": "db_metadata",
    "primarys": "id",
    "ui_config": {
        "table": {
            "default-sort": {prop: 'id', order: 'ascending'}, // descending, ascending
            "size": "small",
            // ...
        },
        "pagination": {
            // ...
            "page-size": DICT.PAGE_NUM_AREA[0],
            "page-sizes": DICT.PAGE_NUM_AREA,
            "current-page": 1,
        }
    }
}

// 主表元字段
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
        "ui_config": {
            "sortable": true,
            "showable": true,
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
        "ui_config": {
            "sortable": true,
            "showable": true,
        }
    }
]

// 子表元对象
const slaveMetadata = {
    "id": "1",
    "code": "meta_field_code",
    "name": "元字段",
    "table_name": "meta_field",
    "schema_name": "db_metadata",
    "primarys": "id",
    "ui_config": {
        "default_sort": {prop: 'id', order: 'descending'},
        "size": "mini",
    }
}

// 子表元字段
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
        "ui_config": {
            "sortable": true,
            "showable": true,
        }
    }
]

const masterData = [{
    id: 1,
    object_code: "meta_object_code"
}, {
    id: 2,
    object_code: "meta_field_code"
}, {
    id: 3,
    object_code: "test3"
}, {
    id: 4,
    object_code: "test4"
}, {
    id: 5,
    object_code: "test5"
}, {
    id: 6,
    object_code: "test6"
}, {
    id: 7,
    object_code: "test7"
}, {
    id: 8,
    object_code: "test8"
}, {
    id: 9,
    object_code: "test9"
}, {
    id: 10,
    object_code: "test10"
}, {
    id: 11,
    object_code: "test11"
}, {
    id: 12,
    object_code: "test12"
}, {
    id: 13,
    object_code: "test13"
}]


// ######################## FormTmpl
const formMeta = {
    "id": "1",
    "code": "meta_object_code",
    "name": "元对象",
    "table_name": "meta_object",
    "primarys": "id",
    "columns": [
        {
            "object_code": "meta_object_code",
            "is_primary": "false",
            "en": "schema_name",
            "cn": "数据源",
            "order_num": 1,
            "component_name": "DropDownBox",
            "ui_config": {
                "show_label": true,
                rules: [{
                    required: true,
                }]
                // ...
            },
        }, {
            "object_code": "meta_object_code",
            "is_primary": "false",
            "en": "table_name",
            "cn": "元数据表",
            "order_num": 2,
            "component_name": "DropDownBox",
            "ui_config": {
                "show_label": true,
                filterable: true,
                rules: [{
                    required: true,
                }]
                // ...
            }
        }, {
            "object_code": "meta_object_code",
            "is_primary": "false",
            "en": "name",
            "cn": "元数据对象",
            "order_num": 2,
            "component_name": "TextBox",
            "ui_config": {
                "show_label": true,
                rules: [{
                    required: true,
                }]
                // ...
            }
        }, {
            "object_code": "meta_object_code",
            "is_primary": "false",
            "en": "code",
            "cn": "元对象编码",
            "order_num": 2,
            "component_name": "TextBox",
            "ui_config": {
                "show_label": true,
                rules: [{
                    required: true,
                }]
                // ...
            }
        }
    ]
}


// export
export default {
    masterSearchBarMetadata,
    masterMetadata,
    masterFieldMetadata,
    slaveMetadata,
    slaveFieldMetadata,
    masterData,
    formMeta
}
