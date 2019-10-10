
// 主表搜索条元数据
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
            "size": "small" // mini, small, medium
        },
        "rules": [{
            "pattern": /\w+$/,
            "message": "只能包含字母数字和下划线",
            "trigger": "blur"
        }, {
            "required": true,
            "message": "对象编码不能为空",
            "trigger": "blur"
        }],
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
        "default_order": "id desc",
        "size": "small",
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
            "sortable": false,
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
        "default_order": "id desc",
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
    masterSearchBarMetadata,
    masterMetadata,
    masterFieldMetadata,
    slaveMetadata,
    slaveFieldMetadata,
    masterData
}
