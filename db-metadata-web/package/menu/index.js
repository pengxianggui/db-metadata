const metaMenus = [{
    "path": "/meta",
    "title": "平台维护",
    "icon": "el-icon-s-tools",
    "hidden": false,
    "disable": false,
    "order": 999999, // 平台维护总是排最后
    "auths": ['menu:platform-ops'],
    "children": [
        {
            "path": "/meta/manager",
            "title": "元数据管理",
            "icon": "meta_data",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_data']
        },
        {
            "path": "/meta/feature",
            "title": "功能维护",
            "icon": "feature",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_feature']
        },
        {
            "path": "/meta/global-conf-list",
            "title": "组件全局配置",
            "icon": "component",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_component']
        },
        {
            "path": "/meta/instance-conf-list",
            "title": "组件实例配置",
            "icon": "component_instance",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_component_instance']
        },
        {
            "path": "/meta/menu",
            "title": "菜单维护",
            "icon": "menu",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_menu']
        },
        {
            "path": "/meta/router",
            "title": "路由维护",
            "icon": "router",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_router']
        },
        {
            "path": "/meta/api-resource",
            "title": "接口资源",
            "icon": "api",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_api_resource']
        },
        {
            "path": "/meta/meta-auth",
            "title": "权限配置",
            "icon": "auth",
            "hidden": false,
            "disable": false,
            "auths": ['menu:meta_auth']
        },
        // {
        //     "path": "/meta/meta-conf",
        //     "title": "MetaConf",
        //     "icon": "el-icon-s-tools",
        //     "hidden": true,
        //     "disable": false,
        //     "auths": ['menu:meta:meta-conf']
        // }
    ]
}]
const programMenus = []

function registerMenu(Vue, opts = {}) {
    const {menus = []} = opts
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
