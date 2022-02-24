import {access} from "../access";

const metaMenus = [{
    "path": "/meta",
    "title": "平台维护",
    "icon": "el-icon-s-tools",
    "hidden": false,
    "disable": false,
    "orderNum": Number.MAX_VALUE, // 平台维护总是排最后
    "children": [
        {
            "path": "/meta/meta-data",
            "title": "元数据管理",
            "icon": "el-icon-warning",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:meta-data']
            }
        },
        {
            "path": "/meta/feature",
            "title": "功能维护",
            "icon": "el-icon-warning-outline",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:feature']
            }
        },
        {
            "path": "/meta/global-conf-list",
            "title": "组件全局配置",
            "icon": "el-icon-star-off",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:component']
            }
        },
        {
            "path": "/meta/instance-conf-list",
            "title": "组件实例配置",
            "icon": "el-icon-star-on",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:component-instance']
            }
        },
        {
            "path": "/meta/meta-menu",
            "title": "菜单维护",
            "icon": "el-icon-star-on",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:menu']
            }
        },
        {
            "path": "/meta/meta-router",
            "title": "路由维护",
            "icon": "el-icon-star-on",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:router']
            }
        },
        {
            "path": "/meta/form-builder",
            "title": "表单构建",
            "icon": "el-icon-s-order",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:form-builder']
            }
        },
        {
            "path": "/meta/meta-conf",
            "title": "MetaConf",
            "icon": "el-icon-s-tools",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:meta-conf']
            }
        },
        {
            "path": "/meta/meta-dict",
            "title": "字典",
            "icon": "el-icon-collection",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:dict']
            }
        },
        {
            "path": "/meta/meta-exception",
            "title": "异常",
            "icon": "el-icon-warning",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:exception']
            }
        },
        {
            "path": "/meta/meta-auth",
            "title": "权限配置",
            "icon": "el-icon-warning",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:auth']
            }
        },
        {
            "path": "/meta/api-resource",
            "title": "接口资源",
            "icon": "el-icon-warning",
            "hidden": false,
            "disable": false,
            "meta": {
                "auths": ['menu:meta:api-resource']
            }
        }
    ]
}]
const programMenus = []

function registerMenu(Vue, opts = {}) {
    const {menus} = opts
    programMenus.push(...menus)
}

/**
 * Meta 平台维护 菜单数据
 */
export default {
    metaMenus,
    programMenus,
    registerMenu
}
