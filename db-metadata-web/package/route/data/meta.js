import MetaDataManager from "../../meta/meta-data-manager/MetaDataManager";
import MetaFeatureList from "../../meta/feature";
import GlobalConfList from "../../meta/global-component/GlobalConfList";
import GlobalConf from "../../meta/global-component/GlobalConf";
import InstanceConfList from "../../meta/instance-component/InstanceConfList";
import RouterManager from "../../meta/route/RouterManager";
import MenuManager from "../../meta/menu/MenuManager";
import FormBuilder from "../../meta/form-builder";
import MetaConfList from "../../meta/meta-conf";
import InstanceConfEditor from "../../meta/instance-component/InstanceConfEditor";
import ApiResourceList from "../../page/api-resource/ApiResourceList";

const routes = [
    {
        path: '/meta/manager',
        name: 'MetaDataManager',
        meta: {
            title: "元数据管理",
            icon: "el-icon-warning",
            noCache: false,
            auths: ['route:meta:manager']
        },
        component: MetaDataManager
    }, {
        path: '/meta/feature',
        name: 'MetaFeatureList',
        meta: {
            title: '功能维护',
            icon: 'el-icon-warning-outline',
            noCache: false,
            auths: ['route:meta:feature']
        },
        component: MetaFeatureList
    }, {
        path: '/meta/global-conf-list',
        name: 'GlobalConfList',
        meta: {
            title: "组件全局配置",
            icon: "el-icon-star-off",
            noCache: false,
            auths: ['route:meta:global-conf-list']
        },
        component: GlobalConfList
    }, {
        path: '/meta/global-conf',
        name: 'GlobalConf',
        meta: {
            title: "组件全局配置-编辑",
            icon: "el-icon-star-off",
            noCache: false,
            auths: ['route:meta:global-conf-edit']
        },
        hidden: true,
        component: GlobalConf
    }, {
        path: '/meta/instance-conf-list',
        name: 'InstanceConfList',
        meta: {
            title: "组件实例配置",
            icon: "el-icon-star-on",
            noCache: false,
            auths: ['route:meta:instance-conf-list']
        },
        component: InstanceConfList
    }, {
        path: '/meta/router',
        name: 'RouterManager',
        meta: {
            title: "路由维护",
            icon: "el-icon-star-off",
            noCache: false,
            auths: ['route:meta:router']
        },
        hidden: false,
        component: RouterManager
    }, {
        path: '/meta/menu',
        name: 'MenuManager',
        meta: {
            title: "菜单维护",
            icon: "el-icon-star-off",
            noCache: false,
            auths: ['route:meta:menu']
        },
        hidden: false,
        component: MenuManager
    }, {
        path: '/meta/api-resource',
        name: 'ApiResourceList',
        meta: {
            title: "接口资源",
            icon: "api",
            noCache: false,
            auths: ['route:meta:api-resource']
        },
        hidden: false,
        component: ApiResourceList
    }, {
        path: '/meta/form-builder',
        name: 'FormBuilder',
        meta: {
            title: "表单构建",
            icon: "el-icon-s-order",
            noCache: false,
            auths: ['route:meta:form-builder']
        },
        props: (route) => ({
            ic: route.query.ic,
            oc: route.query.oc,
            cc: route.query.cc
        }),
        component: FormBuilder
    }, {
        path: '/meta/meta-conf',
        name: 'MetaConfList',
        meta: {
            title: 'MetaConf',
            icon: 'el-icon-s-tools',
            noCache: false,
            auths: ['route:meta:meta-conf']
        },
        component: MetaConfList
    }, {
    //     path: '/meta/dict',
    //     name: 'DictList',
    //     meta: {
    //         title: '字典',
    //         icon: 'el-icon-collection',
    //         noCache: false,
    //         auths: ['route:meta:dict']
    //     },
    //     component: DictList
    // }, {
    //     path: '/meta/exception',
    //     name: 'ExceptionList',
    //     meta: {
    //         title: '异常',
    //         icon: 'el-icon-warning',
    //         noCache: false,
    //         auths: ['route:meta:exception']
    //     },
    //     component: ExceptionList
    // }, {
    //     path: '/meta/auth',
    //     name: 'AuthList',
    //     meta: {
    //         title: '权限配置',
    //         icon: 'el-icon-warning',
    //         noCache: false,
    //         auths: ['route:meta:auth']
    //     },
    //     component: AuthList
    // }, {
    //     path: '/meta/api-resource',
    //     name: 'ApiResourceList',
    //     meta: {
    //         title: '接口资源',
    //         icon: 'el-icon-warning',
    //         noCache: false,
    //         auths: ['route:meta:api-resource']
    //     },
    //     component: ApiResourceList
    // }, {
        path: '/meta/instance-conf-edit',
        component: InstanceConfEditor,
        name: 'InstanceConfEditor',
        meta: {
            title: '组件实例配置-编辑',
            icon: 'edit',
            noCache: false,
            auths: ['route:meta:instance-conf-edit']
        },
        hidden: true,
        props: (route) => ({
            ic: route.query.instanceCode,
            fc: route.query.fieldCode
        })
    }
]

export default function () {
    return routes
}
