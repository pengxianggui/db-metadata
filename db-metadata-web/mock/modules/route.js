/**
 * 当前路由和菜单推荐采用 一对一 关系.
 * 而之前采用的方式是一对多的关系，例如：将单表的组件渲染菜单都映射到 单表对应的路由: /main/table，通过fc参数差异化组件数据内容
 * 但是这样会带来一个问题：当有tagViews标签页时, 无法做缓存cache, 因为两个菜单指向的路由其实是同一个, 缓存keep-alive根据路由的name来的
 *
 * 因此建议路由和菜单一对一 对应（菜单是少于路由的，因为有些路由无需在菜单中出现）
 * @type {({redirect: string, path: string, component: string, children: ({path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string}|{path: string, component: string, meta: {cache: boolean, roles: []}, name: string})[], name: string}|{path: string, component: string, children: ({path: string, component: string, name: string}|{path: string, component: string, name: string}|{path: string, component: string, name: string}|{path: string, component: string, name: string}|{path: string, component: string, name: string})[], name: string})[]}
 */
export const routes = [
    {
        "path": "/main",
        "name": "main",
        "redirect": "/main/meta-data",
        "components": {
            default: "Management",
            menu: "NavMenu"
        },
        "children": [
            {
                "path": "meta-data",
                "name": "Metadata",
                "component": "MetaDataManager",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "feature",
                "name": "Feature",
                "component": "FeatureList",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "global-conf-list",
                "name": "MetaComponent",
                "component": "GlobalConfList",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "global-conf",
                "name": "MetaComponentEdit",
                "component": "GlobalConf",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "instance-conf-list",
                "name": "MetaComponentInstance",
                "component": "InstanceConfList",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "instance-conf-new",
                "name": "InstanceConfNew",
                "component": "InstanceConfNew",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "form-builder",
                "name": "FormBuilder",
                "component": "FormBuilder",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "meta-conf",
                "name": "MetaConf",
                "component": "MetaConf",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "meta-dict",
                "name": "MetaDict",
                "component": "DataList",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "meta-exception",
                "name": "MetaException",
                "component": "ExceptionList",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "instance-conf-edit",
                "name": "InstanceConfEdit",
                "component": "InstanceConfEdit",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "objects",
                "name": "objects",
                "component": "ObjectsTmpl",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            },
            {
                "path": "demo",
                "name": "Demo",
                "component": "Demo",
                "meta": {
                    "roles": [],
                    "cache": false
                }
            }
        ]
    },
    {
        "path": "/main",
        "name": "business",
        "component": "Management",
        "children": [
            {
                "path": "ms-table",
                "name": "msTable",
                "component": "MasterSlaveTableTmpl"
            },
            {
                "path": "ding-user",
                "name": "dingUser",
                "component": "SingleGridTmpl"
            },
            {
                "path": "magpie-user",
                "name": "magpieUser",
                "component": "SingleGridTmpl"
            },
            {
                "path": "gitlab-user",
                "name": "gitlabUser",
                "component": "SingleGridTmpl"
            },
            {
                "path": "jira-user",
                "name": "jiraUser",
                "component": "SingleGridTmpl"
            },
            {
                "path": "jira-project",
                "name": "jiraProject",
                "component": "SingleGridTmpl"
            }
        ]
    }
]

export default [
    {
        url: '/route/tree',
        type: 'get',
        disable: false,
        response: config => {
            console.log('mock: /route/tree');
            return {
                code: 200,
                state: 'ok',
                data: routes
            }
        }
    }
]