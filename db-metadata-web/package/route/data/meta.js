import MetaDataManager from "../../meta/meta-data-manager/MetaDataManager";
import MetaFeatureList from "../../meta/feature";
import GlobalConfList from "../../meta/global-component/GlobalConfList";
import GlobalConf from "../../meta/global-component/GlobalConf";
import InstanceConfList from "../../meta/instance-component/InstanceConfList";
import RouterManager from "../../meta/route/RouterManager";
import MenuManager from "../../meta/menu/MenuManager";
import FormBuilder from "../../meta/form-builder";
import MetaConfList from "../../meta/meta-conf";
import DictList from "../../meta/dict";
import ExceptionList from "../../meta/exception";
import AuthList from "../../meta/rbac/AuthList";
import ApiResourceList from "../../meta/rbac/ApiResourceList";
import InstanceConfEdit from "../../meta/instance-component/InstanceConfEdit";
import utils from "../../utils";
import Redirect from "../redirect";


/**
 * Meta 平台维护路由数据
 * @type {any}
 */
const jumpOut = [
    {
        path: 'instance-conf-edit',
        component: InstanceConfEdit,
        name: 'InstanceConfEdit',
        meta: {
            title: '组件实例配置-编辑',
            icon: 'edit',
            noCache: false,
            auths: ['route:meta:instance-conf-edit']
        },
        hidden: true,
        props: (route) => ({
            instanceCode: route.query.instanceCode,
            objectCode: route.query.objectCode,
            componentCode: route.query.componentCode,
            fieldCode: utils.assertEmpty(route.query.fieldCode, '')
        })
    }
];

/**
 * 外层路由，用于全页面打开ui-conf编辑等
 * @type {*[]}
 */
const outerRoute = jumpOut.map(route => {
    let item = utils.deepClone(route);
    item.path = '/' + route.path
    item.name = 'G_' + route.name // 避免重名
    item.meta.newTab = true // newTab表示用于新开窗口
    return item;
});

const routes = [
    {
        path: '/meta/manager',
        name: 'Metadata',
        meta: {
            title: "元数据管理",
            icon: "el-icon-warning",
            noCache: false,
            auths: ['route:meta:manager']
        },
        component: MetaDataManager
    }, {
        path: '/meta/feature',
        name: 'Feature',
        meta: {
            title: '功能维护',
            icon: 'el-icon-warning-outline',
            noCache: false,
            auths: ['route:meta:feature']
        },
        component: MetaFeatureList
    }, {
        path: '/meta/global-conf-list',
        name: 'MetaComponent',
        meta: {
            title: "组件全局配置",
            icon: "el-icon-star-off",
            noCache: false,
            auths: ['route:meta:global-conf-list']
        },
        component: GlobalConfList
    }, {
        path: '/meta/global-conf',
        name: 'MetaComponentEdit',
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
        name: 'MetaComponentInstance',
        meta: {
            title: "组件实例配置",
            icon: "el-icon-star-on",
            noCache: false,
            auths: ['route:meta:instance-conf-list']
        },
        component: InstanceConfList
    }, {
        path: '/meta/router',
        name: 'MetaRouter',
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
        name: 'MetaMenu',
        meta: {
            title: "菜单维护",
            icon: "el-icon-star-off",
            noCache: false,
            auths: ['route:meta:menu']
        },
        hidden: false,
        component: MenuManager
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
        name: 'MetaConf',
        meta: {
            title: 'MetaConf',
            icon: 'el-icon-s-tools',
            noCache: false,
            auths: ['route:meta:meta-conf']
        },
        component: MetaConfList
    }, {
        path: '/meta/dict',
        name: 'MetaDict',
        meta: {
            title: '字典',
            icon: 'el-icon-collection',
            noCache: false,
            auths: ['route:meta:dict']
        },
        component: DictList
    }, {
        path: '/meta/exception',
        name: 'MetaException',
        meta: {
            title: '异常',
            icon: 'el-icon-warning',
            noCache: false,
            auths: ['route:meta:exception']
        },
        component: ExceptionList
    }, {
        path: '/meta/auth',
        name: 'MetaAuth',
        meta: {
            title: '权限配置',
            icon: 'el-icon-warning',
            noCache: false,
            auths: ['route:meta:auth']
        },
        component: AuthList
    }, {
        path: '/meta/api-resource',
        name: 'ApiResource',
        meta: {
            title: '接口资源',
            icon: 'el-icon-warning',
            noCache: false,
            auths: ['route:meta:api-resource']
        },
        component: ApiResourceList
    },
    ...jumpOut
]

export default function (Layout) {
    return [
        ...routes,
        {
            path: '/__redirect',
            component: Layout,
            hidden: true,
            children: [
                {
                    path: '/__redirect/:path(.*)',
                    component: Redirect
                }
            ]
        },
        ...outerRoute
    ]
}
