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
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/feature",
            "title": "功能维护",
            "icon": "el-icon-warning-outline",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/global-conf-list",
            "title": "组件全局配置",
            "icon": "el-icon-star-off",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/instance-conf-list",
            "title": "组件实例配置",
            "icon": "el-icon-star-on",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/meta-menu",
            "title": "菜单维护",
            "icon": "el-icon-star-on",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/meta-router",
            "title": "路由维护",
            "icon": "el-icon-star-on",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/form-builder",
            "title": "表单构建",
            "icon": "el-icon-s-order",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/meta-conf",
            "title": "MetaConf",
            "icon": "el-icon-s-tools",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/meta-dict",
            "title": "字典",
            "icon": "el-icon-collection",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/meta-exception",
            "title": "异常",
            "icon": "el-icon-warning",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
            }
        },
        {
            "path": "/meta/meta-auth",
            "title": "权限配置",
            "icon": "el-icon-warning",
            "hidden": false,
            "disable": false,
            "meta": {
                "roles": [access.root]
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
