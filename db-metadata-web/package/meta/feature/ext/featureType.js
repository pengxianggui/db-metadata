/**
 * 各个功能模板的初始config配置
 * @type {{MasterSlaveGrid: {getInitConf: (function(*=, *=): {slaves: [{objectCode: null, foreignFieldCode: null, order: number}], master: {objectCode: *, primaryKey: *}})}, TreeAndTable: {getInitConf: (function(*=, *=): {tree: {pidKey: null, idKey: *, objectCode: null, label: null, rootIdentify: null, isSync: boolean}, table: {objectCode: *, foreignFieldCode: null, primaryKey: null}})}, TreeInTable: {getInitConf: (function(*=): {table: {pidKey: null, idKey: null, objectCode: *, label: null, rootIdentify: null, isSync: boolean}})}, SingleGrid: {getInitConf: (function(*=): {singleGrid: {objectCode: *}})}}}
 */
export const FEATURE_TYPE_MAPPING = {
    'MasterSlaveGrid': {
        getInitConf: function (objectCode, primaryKey) {
            return {
                master: {
                    objectCode: objectCode,
                    primaryKey: primaryKey
                },
                slaves: [{
                    objectCode: null,
                    foreignFieldCode: null,
                    order: 0
                }]
            }
        }
    },
    'SingleGrid': {
        getInitConf: function (objectCode) {
            return {
                singleGrid: {
                    objectCode: objectCode
                }
            }
        }
    },
    'TreeInTable': {
        getInitConf: function (objectCode) {
            return {
                table: {
                    objectCode: objectCode,
                    idKey: null,
                    pidKey: null,
                    rootIdentify: null,
                    label: null,
                    isSync: false
                }
            }
        }
    },
    'TreeAndTable': {
        getInitConf: function (objectCode, primaryKey) {
            return {
                tree: {
                    objectCode: null,
                    idKey: primaryKey,
                    pidKey: null,
                    rootIdentify: null,
                    label: null,
                    isSync: false,
                    // primaryKey: primaryKey
                },
                table: {
                    objectCode: objectCode,
                    primaryKey: null,
                    foreignFieldCode: null
                }
            }
        }
    }
};

// 单表功能的默认配置
export const SingleGridConfig = {
    "config": {
        "objectCode": null,
    },
    "instanceCodes": {
        "SearchView": null,
        "TableView": null,
        "FormView": null
    }
}
// 单表功能配置的FormView配置
export const SingleGridConfigFormMeta = {
    component_name: 'MiniForm',
    conf: {
        "label-position": 'top'
    },
    columns: [
        {
            component_name: 'MiniForm',
            name: 'config',
            label: '配置',
            conf: {
                "label-position": 'top',
                'label-width': '120px'
            },
            columns: [
                {
                    component_name: 'DropDownBox',
                    name: 'objectCode',
                    label: '元对象编码',
                    data_url: '/table/list?objectCode=meta_object&fs=code,cn&code->key=&code->value=&cn->label=&s=1000'
                }
            ]
        },
        {
            component_name: 'MiniForm',
            name: 'instanceCodes',
            label: '选择容器UI实例',
            conf: {
                'label-width': '120px'
            },
            columns: [
                {
                    component_name: 'DropDownBox',
                    name: 'SearchView',
                    label: '搜索面板',
                    data_url: '/table/list?objectCode=meta_component_instance&fs=code,name&code->key=&code->value=&name->label=&s=1000&type=META_OBJECT'
                },
                {
                    component_name: 'DropDownBox',
                    name: 'TableView',
                    label: '表格',
                    data_url: '/table/list?objectCode=meta_component_instance&fs=code,name&code->key=&code->value=&name->label=&s=1000&type=META_OBJECT'
                },
                {
                    component_name: 'DropDownBox',
                    name: 'FormView',
                    label: '表单',
                    data_url: '/table/list?objectCode=meta_component_instance&fs=code,name&code->key=&code->value=&name->label=&s=1000&type=META_OBJECT'
                }
            ]
        }
    ]
}

// 树表功能的默认配置
export const TreeSingleGridConfig = {
    "config": {
        "objectCode": null,
        "idKey": null,
        "pidKey": null,
        "rootIdentify": null,
        "label": null,
        "isSync": false,
    },
    "instanceCodes": {
        "SearchView": null,
        "TableTreeView": null,
        "FormView": null
    }
}

// 主子表功能的默认配置
export const MasterSlaveGridConfig = {
    "master": {
        "config": {
            "objectCode": null,
            "primaryKey": null,
        },
        "instanceCodes": {
            "SearchView": null,
            "TableView": null,
            "FormView": null
        }
    },
    "slaves": [
        {
            "config": {
                "objectCode": null,
                "foreignPrimaryKey": null,
                "order": 0
            },
            "instanceCodes": {
                "SearchView": null,
                "TableView": null,
                "FormView": null
            }
        }
    ]
}

// 树+表功能的默认配置
export const TreeAndTableConfig = {
    "tree": {
        "config": {
            "objectCode": null,
            "idKey": null,
            "pidKey": null,
            "rootIdentify": null,
            "label": null,
            "isSync": false,
            "primaryKey": null
        },
        "instanceCodes": {
            "TreeView": null
        }
    },
    "table": {
        "config": {
            "objectCode": null,
            "foreignPrimaryKey": null
        },
        "instanceCodes": {
            "SearchView": null,
            "TableView": null,
            "FormView": null
        }
    }
}

// 表单功能的默认配置
export const FormConfig = {
    "config": {
        "objectCode": null
    },
    "instanceCodes": {
        "FormView": null
    }
}

// 树+表单功能的默认配置
export const TreeAndFormConfig = {
    "tree": {
        "config": {
            "objectCode": null,
            "idKey": null,
            "pidKey": null,
            "rootIdentify": null,
            "label": null,
            "isSync": false,
            "primaryKey": null
        },
        "instanceCodes": {
            "TreeView": null
        }
    },
    "form": {
        "config": {
            "objectCode": null
        },
        "instanceCodes": {
            "FormView": null
        }
    }
}

// 表+表单功能的默认配置
export const TableAndFormConfig = {
    "table": {
        "config": {
            "objectCode": null,
            "primaryKey": null
        },
        "instanceCodes": {
            "SearchView": null,
            "TableView": null,
            "FormView": null
        }
    },
    "form": {
        "config": {
            "objectCode": null
        },
        "instanceCodes": {
            "FormView": null
        }
    }
}

// TODO 3.0 可以通过提供 默认配置和 功能新增表单的实例配置 来前端扩展。这样的话，不同功能，就不必单独开发功能新增的配置界面了。
