import MetaDataManager from "../meta/MetaDataManager";
import FormBuilder from '../meta/form-builder'
import GlobalConfList from "../meta/component/GlobalConfList";
import GlobalConf from "../meta/component/GlobalConf";
import InstanceConfList from "../meta/component-instance/InstanceConfList";
import InstanceConfEdit from "../meta/component-instance/InstanceConfEdit";
import InstanceConfNew from "../meta/component-instance/InstanceConfNew";
import RouterManager from "../meta/route/RouterManager";
import MenuManager from "../meta/menu/MenuManager";
import MetaFeatureList from '../meta/feature';
import MetaConfList from "../meta/meta-conf";
import DictList from "../meta/dict"
import ExceptionList from '../meta/exception'
import AdminLayout from '../layout/admin-layout'

import {access} from '../constant/variable'
import utils from "../utils";
import {restUrl} from "../constant/url";
import exchange from "./exchange";

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
            roles: [access.root]
        },
        hidden: true
    }, {
        path: 'instance-conf-new',
        name: 'InstanceConfNew',
        meta: {
            title: "组件实例配置-新增",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.root]
        },
        hidden: true,
        component: InstanceConfNew
    }
];

export const innerRoute = [
    {
        path: 'meta-data',
        name: 'Metadata',
        meta: {
            title: "元数据管理",
            icon: "el-icon-warning",
            noCache: false,
            roles: [access.root]
        },
        component: MetaDataManager
    }, {
        path: 'feature',
        name: 'Feature',
        meta: {
            title: '功能维护',
            icon: 'el-icon-warning-outline',
            noCache: false,
            roles: [access.root]
        },
        component: MetaFeatureList
    }, {
        path: 'global-conf-list',
        name: 'MetaComponent',
        meta: {
            title: "组件全局配置",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.root]
        },
        component: GlobalConfList
    }, {
        path: 'global-conf',
        name: 'MetaComponentEdit',
        meta: {
            title: "组件全局配置-编辑",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.root]
        },
        hidden: true,
        component: GlobalConf
    }, {
        path: 'instance-conf-list',
        name: 'MetaComponentInstance',
        meta: {
            title: "组件实例配置",
            icon: "el-icon-star-on",
            noCache: false,
            roles: [access.root]
        },
        component: InstanceConfList
    }, {
        path: 'meta-router',
        name: 'MetaRouter',
        meta: {
            title: "路由维护",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.root]
        },
        hidden: false,
        component: RouterManager
    }, {
        path: 'meta-menu',
        name: 'MetaMenu',
        meta: {
            title: "菜单维护",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.root]
        },
        hidden: false,
        component: MenuManager
    }, {
        path: 'form-builder',
        name: 'FormBuilder',
        meta: {
            title: "表单构建",
            icon: "el-icon-s-order",
            noCache: false,
            roles: [access.root]
        },
        component: FormBuilder
    }, {
        path: 'meta-conf',
        name: 'MetaConf',
        meta: {
            title: 'MetaConf',
            icon: 'el-icon-s-tools',
            noCache: false,
            roles: [access.root]
        },
        component: MetaConfList
    }, {
        path: 'meta-dict',
        name: 'MetaDict',
        meta: {
            title: '字典',
            icon: 'el-icon-collection',
            noCache: false,
            roles: [access.root]
        },
        component: DictList
    }, {
        path: 'meta-exception',
        name: 'MetaException',
        meta: {
            title: '异常',
            icon: 'el-icon-warning',
            noCache: false,
            roles: [access.root]
        },
        component: ExceptionList
    },
    ...jumpOut
]

/**
 * 外层路由，用于全页面打开ui-conf编辑
 * @type {*[]}
 */
export const outerRoute = jumpOut.map(route => {
    let item = utils.deepClone(route);
    item.path = '/' + route.path;
    item.name = 'G_' + route.name; // 避免重名
    return item;
});

function metaRoute(layout) {
    return [
        {
            path: '/meta',
            component: layout,
            redirect: '/meta/meta-data',
            children: [
                ...innerRoute
            ]
        },
        {
            path: '/__redirect',
            component: layout,
            hidden: true,
            children: [
                {
                    path: '/__redirect/:path(.*)',
                    component: () => import('./redirect')
                }
            ]
        },
        ...outerRoute
    ]
}

function addMetaRoutes(router, Layout = AdminLayout) {
    router.addRoutes(metaRoute(Layout))
}

function addDynamicRoutes(router, Layout = AdminLayout, axios, url = restUrl.ROUTE_DATA, formatCallback = exchange) {
    axios.get(url).then(resp => {
        const {data: routes} = resp
        console.info('[MetaElement] 装配动态路由, %o', routes)
        const dynamicRoutes = formatCallback(routes, Layout)
        console.debug('[MetaElement] 动态路由数据为: %o', dynamicRoutes)
        router.addRoutes(dynamicRoutes)
    })
}

// export default function (router, Layout, axios, url, formatCallback) {
//     addMetaRoutes(router, Layout)
//     addDynamicRoutes(router, Layout, axios, url, formatCallback)
// }

export default class RouteLoader {
    addRoutes(router, Layout, axios, url, formatCallback) {
        addMetaRoutes(router, Layout)
        addDynamicRoutes(router, Layout, axios, url, formatCallback)
    }
}