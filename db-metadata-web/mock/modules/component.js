import {defaultMeta} from "../../package/core/index";
import utils from "../../package/utils";

const componentMetas = {
    'meta_object&DataList': {
        "data_url": "/table/list?objectCode=meta_object",
        "conf": {
            "label-props": {"label": "code"}
        }
    },
    'meta_object&Tree': {
        "component_name": "Tree",
        "name": "Tree",
        "label": "Tree",
        "data_url": "/tree/list?objectCode=meta_object",
        "conf": {
            "props": {"label": "name"},
            "show-checkbox": true
        }
    },
};

const treeData = [
    {
        "id": "410126905489100800",
        "name": "meta_object1",
        "code": "meta_object",
        "children": [
            {
                "id": "410126905245831168",
                "name": "meta_field",
                "code": "meta_field"
            },
            {
                "id": "410126904214032384",
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


export default [
    {
        url: '/component/meta',
        type: 'get',
        disable: true,
        response: config => {
            const {objectCode: oc, componentCode: cc} = config.query;
            console.log('mock: /component/meta, oc: %s, cc: %s', oc, cc);
            const data = utils.merge(componentMetas[oc + '&' + cc], defaultMeta[cc]);
            debugger;
            return {
                code: 200,
                state: 'ok',
                data: data
            }
        }
    },
    {
        url: '/tree/list?objectCode=meta_object',
        type: 'get',
        response: config => {
            console.log('mock: /tree/list?objectCode=meta_object');
            return {
                code: 200,
                state: 'ok',
                data: treeData
            }
        }
    }
]
