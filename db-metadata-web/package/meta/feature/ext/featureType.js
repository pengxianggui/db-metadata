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

/**
 * 各类型功能的配置初始结构和对应MiniForm的meta数据。
 *
 * 注意: meta数据虽然好的避免了不同功能的迷你表单的差异开发，但是由于目标表单还做不到级联配置化。所以功能配置上迷你表单中的组件无法联动，难以接受。
 * 因此meta数据暂时没有用到，而是使用component直接替代。
 *
 * @type {{MasterSlaveGrid: {meta: {}, config: {slaves: [{config: {foreignPrimaryKey: null, objectCode: null, order: number}, instanceCodes: {SearchView: null, FormView: null, TableView: null}}], master: {config: {objectCode: null, primaryKey: null}, instanceCodes: {SearchView: null, FormView: null, TableView: null}}}}, TreeAndTable: {meta: {}, config: {tree: {config: {pidKey: null, idKey: null, objectCode: null, label: null, rootIdentify: null, isSync: boolean, primaryKey: null}, instanceCodes: {TreeView: null}}, table: {config: {foreignPrimaryKey: null, objectCode: null}, instanceCodes: {SearchView: null, FormView: null, TableView: null}}}}, FormConfig: {meta: {}, config: {config: {objectCode: null}, instanceCodes: {FormView: null}}}, TreeSingleGrid: {meta: {}, config: {config: {pidKey: null, idKey: null, objectCode: null, label: null, rootIdentify: null, isSync: boolean}, instanceCodes: {TableTreeView: null, SearchView: null, FormView: null}}}, TableAndForm: {meta: {}, config: {form: {config: {objectCode: null}, instanceCodes: {FormView: null}}, table: {config: {objectCode: null, primaryKey: null}, instanceCodes: {SearchView: null, FormView: null, TableView: null}}}}, SingleGrid: {meta: {columns: [{columns: [{component_name: string, name: string, label: string, data_url: string}], component_name: string, name: string, conf: {"label-position": string, "label-width": string}, label: string}, {columns: [{component_name: string, name: string, label: string, data_url: string}, {component_name: string, name: string, label: string, data_url: string}, {component_name: string, name: string, label: string, data_url: string}], component_name: string, name: string, conf: {"label-width": string}, label: string}], component_name: string, conf: {"label-position": string}}, config: {config: {objectCode: null}, instanceCodes: {SearchView: null, FormView: null, TableView: null}}}, TreeAndForm: {meta: {}, config: {form: {config: {objectCode: null}, instanceCodes: {FormView: null}}, tree: {config: {pidKey: null, idKey: null, objectCode: null, label: null, rootIdentify: null, isSync: boolean, primaryKey: null}, instanceCodes: {TreeView: null}}}}}}
 */
export const FEATURE_TYPE = {
    SingleGrid: {
        value: {
            "config": {
                "objectCode": null,
            },
            "instanceCodes": {
                "SearchView": null,
                "TableView": null,
                "FormView": null
            }
        },
        meta: {
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
                        'label-width': '60px'
                    },
                    columns: [
                        {
                            component_name: 'DropDownBox',
                            name: 'objectCode',
                            label: '元对象编码',
                            data_url: '/table/list?objectCode=meta_object&fs=code,cn&code->key=&code->value=&cn->label=&s=1000',
                            conf: {
                                filterable: true
                            }
                        }
                    ]
                },
                {
                    component_name: 'MiniForm',
                    name: 'instanceCodes',
                    label: '选择容器UI实例',
                    conf: {
                        'label-width': '80px',
                        'size': 'mini',
                        'inline': true
                    },
                    columns: [
                        {
                            component_name: 'DropDownBox',
                            name: 'SearchView',
                            label: '搜索面板',
                            data_url: '/table/list?objectCode=meta_component_instance&fs=code,name&code->key=&code->value=&name->label=&s=1000&type=META_OBJECT',
                            conf: {
                                filterable: true
                            }
                        },
                        {
                            component_name: 'DropDownBox',
                            name: 'TableView',
                            label: '表格',
                            data_url: '/table/list?objectCode=meta_component_instance&fs=code,name&code->key=&code->value=&name->label=&s=1000&type=META_OBJECT',
                            conf: {
                                filterable: true
                            }
                        },
                        {
                            component_name: 'DropDownBox',
                            name: 'FormView',
                            label: '表单',
                            data_url: '/table/list?objectCode=meta_component_instance&fs=code,name&code->key=&code->value=&name->label=&s=1000&type=META_OBJECT',
                            conf: {
                                filterable: true
                            }
                        }
                    ]
                }
            ]
        }
    },
    TreeSingleGrid: {
        value: {
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
        },
        meta: {}
    },
    MasterSlaveGrid: {
        value: {
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
        },
        meta: {}
    },
    TreeTable: {
        value: {
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
        },
        meta: {}
    },
    FormConfig: {
        value: {
            "config": {
                "objectCode": null
            },
            "instanceCodes": {
                "FormView": null
            }
        },
        meta: {}
    },
    TreeAndForm: {
        value: {
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
        },
        meta: {}
    },
    TableAndForm: {
        value: {
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
        },
        meta: {}
    }
}

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
