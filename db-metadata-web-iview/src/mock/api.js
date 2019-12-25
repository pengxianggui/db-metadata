import {DEFAULT, URL} from '@/constant'
import commonRoutes from '@/router/commonRoute'
import utils from '@/utils'

import axios from "axios";
import MockAdapter from 'axios-mock-adapter'

let mockAxios = new MockAdapter(axios);

const mockMenus = [
    {
        path: 'business',
        icon: 'el-icon-s-custom',
        label: '业务模块',
        children: [{
            path: '/main/table?objectCode=test_table',
            icon: 'el-icon-s-help',
            label: '单表CRUD'
        }, {
            path: '/main/ms-table?featureCode=meta_manager',
            icon: 'el-icon-s-help',
            label: '主子表'
        }, {
            path: '/main/demo',
            icon: 'el-icon-menu',
            label: '组件概览'
        }, {
            path: '/main/objects',
            icon: 'el-icon-menu',
            label: '动态元对象'
        }]
    }, {
        path: 'system',
        icon: 'el-icon-s-grid',
        label: '系统管理',
        children: [{
            path: '/main/table?objectCode=change_log',
            icon: 'el-icon-finished',
            label: 'ChangeLog'
        }, {
            path: '/main/table?featureCode=DbVersionSingleGrid',
            icon: 'el-icon-paperclip',
            label: 'DbVersion'
        }]
    }, {
        path: 'meta',
        icon: 'el-icon-s-tools',
        label: '平台维护',
        children: [{
            path: '/main/table?objectCode=meta_feature',
            icon: 'el-icon-warning-outline',
            label: '功能维护'
        }, {
            path: '/main/meta-data',
            icon: 'el-icon-warning',
            label: '元数据管理'
        }, {
            path: '/main/meta-component',
            icon: 'el-icon-star-off',
            label: '组件全局配置'
        }, {
            path: '/main/meta-component-instance',
            icon: 'el-icon-star-on',
            label: '组件实例配置'
        }, {
            path: '/main/form-builder',
            icon: 'el-icon-s-order',
            label: '表单构建'
        }, {
            path: '/main/table?objectCode=meta_config',
            icon: 'el-icon-s-tools',
            label: 'MetaConfig'
        }, {
            path: '/main/table?objectCode=meta_dict',
            icon: 'el-icon-collection',
            label: '字典'
        }]
    },
];
const listTableFeatureConf = {
    "list": {
        "objectCode": "meta_object",
        "primaryKey": "code"
    },
    "table": {
        "objectCode": "meta_field",
        "foreignFieldCode": "object_code"
    }
};
const treeTableFeatureConf = {
    "tree": {
        "objectCode": "meta_object",
        "primaryKey": "code"
    },
    "table": {
        "objectCode": "meta_field",
        "foreignFieldCode": "object_code"
    }
};
const treeFormFeatureConf = {
    "tree": {
        "objectCode": "test_table",
        "primaryKey": "id",
    },
    "form": {
        "objectCode": "test_table"
    }
};

const mockTree = [
    {
        "id": "1",
        "name": "meta_object1",
        "code": "meta_object",
        "children": [
            {
                "id": "1-1",
                "name": "meta_field",
                "code": "meta_field"
            },
            {
                "id": "1-2",
                "name": "change_log",
                "code": "change_log"
            }
        ]
    },
    {
        "id": "2",
        "name": "一级 2",
        "children": [
            {
                "id": "2-1",
                "name": "二级 2-1",
                "children": [
                    {
                        "id": "2-1-1",
                        "name": "三级 2-1-1"
                    }
                ]
            },
            {
                "id": "2-2",
                "name": "二级 2-2",
                "children": [
                    {
                        "id": "2-2-1",
                        "name": "三级 2-2-1"
                    }
                ]
            }
        ]
    },
    {
        "id": "3",
        "name": "一级 3",
        "children": [
            {
                "id": "3-1",
                "name": "二级 3-1",
                "children": [
                    {
                        "id": "3-1-1",
                        "name": "三级 3-1-1"
                    }
                ]
            },
            {
                "id": "3-2",
                "name": "二级 3-2",
                "children": [
                    {
                        "id": "3-2-1",
                        "name": "三级 3-2-1"
                    }
                ]
            }
        ]
    }
];

mockAxios
// 路由mock --------------------------------------------------------------------------------
    .onGet(URL.ROUTE_DATA).reply(200, {
    data: commonRoutes,
    state: 'ok'
})
// list-table-tmpl mock -------------------------------------------------------------------
    .onGet(utils.compile(URL.FEATURE_LOAD, {featureCode: 'list-table-tmpl'})).reply(200, {
    data: listTableFeatureConf,
    state: 'ok'
}).onGet(utils.compile(URL.COMPONENT_INSTANCE_META, {
    objectCode: "meta_object",
    componentCode: 'DataList'
})).reply(200, {
    data: utils.merge(
        {
            "data_url": "/table/list/meta_object",
            "conf": {
                "label-props": {"label": "code"}
            }
        },
        DEFAULT.DataList),
    state: 'ok'
})
// tree-table-tmpl mock -------------------------------------------------------------------
    .onGet(utils.compile(URL.FEATURE_LOAD, {featureCode: 'tree-table-tmpl'})).reply(200, {
    data: treeTableFeatureConf,
    state: 'ok'
}).onGet(utils.compile(URL.COMPONENT_INSTANCE_META, {
    objectCode: "meta_object",
    componentCode: 'Tree'
})).reply(200, {
    data: utils.merge(
        {
            "component_name": "Tree",
            "name": "Tree",
            "label": "Tree",
            "data_url": "/tree/list/meta_object",
            "conf": {
                "props": {"label": "name"},
                "show-checkbox": true
            }
        },
        DEFAULT.Tree),
    state: 'ok'
}).onGet("/tree/list/meta_object").reply(200, {
    data: mockTree,
    state: 'ok'
})
// tree-form-tmpl ------------------------------------------------------------------------
    .onGet(utils.compile(URL.FEATURE_LOAD, {featureCode: 'tree-form-tmpl-demo'})).reply(200, {
    data: treeFormFeatureConf,
    state: 'ok'
})
    .onGet(utils.compile(URL.COMPONENT_INSTANCE_META, {
        objectCode: "test_table",
        componentCode: "Tree"
    })).reply(200, {
    data: utils.merge(
        {
            "component_name": "Tree",
            "name": "Tree",
            "label": "Tree",
            "data_url": "/tree/list/test_table",
            "conf": {
                "props": {"label": "name"},
                "show-checkbox": true
            }
        },
        DEFAULT.Tree),
    state: 'ok'
}).onGet("/tree/list/test_table").reply(200, {
    data: mockTree,
    state: 'ok'
})
// 其他放行
    .onAny().passThrough();