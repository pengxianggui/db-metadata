import MetaDataManager from "../meta/MetaDataManager";
import FormBuilder from '../meta/form-builder'
import GlobalConfList from "../meta/global-component/GlobalConfList";
import GlobalConf from "../meta/global-component/GlobalConf";
import InstanceConfList from "../meta/instance-component/InstanceConfList";
import InstanceConfEdit from "../meta/instance-component/InstanceConfEdit";
import RouterManager from "../meta/route/RouterManager";
import MenuManager from "../meta/menu/MenuManager";
import MetaFeatureList from '../meta/feature';
import MetaConfList from "../meta/meta-conf";
import DictList from "../meta/dict"
import ExceptionList from '../meta/exception'
import AdminLayout from '../layout/admin-layout'
import Redirect from './redirect'

import {access} from '../access'
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
        hidden: true,
        props: (route) => ({
            instanceCode: route.query.instanceCode,
            objectCode: route.query.objectCode,
            componentCode: route.query.componentCode,
            fieldCode: utils.assertEmpty(route.query.fieldCode, '')
        })
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
        props: (route) => ({
            ic: route.query.ic,
            oc: route.query.oc,
            cc: route.query.cc
        }),
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
 * 外层路由，用于全页面打开ui-conf编辑等
 * @type {*[]}
 */
export const outerRoute = jumpOut.map(route => {
    let item = utils.deepClone(route);
    item.path = '/' + route.path
    item.name = 'G_' + route.name // 避免重名
    item.meta.newTab = true // newTab表示用于新开窗口
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
                    component: Redirect
                }
            ]
        },
        ...outerRoute
    ]
}

async function getDynamicRoutesFromRemote(axios, url) {
    return await axios.get(url)
}

// 装载 meta 路由
function addMetaRoutes(router, Layout = AdminLayout) {
    router.addRoutes(metaRoute(Layout))
}

// 异步装载动态路由
function addDynamicRoutes(router,
                          axios,
                          Layout = AdminLayout,
                          url = restUrl.ROUTE_DATA,
                          formatCallback = exchange) {
    getDynamicRoutesFromRemote(axios, url).then(resp => {
        const {data: routes} = resp
        console.info('[MetaElement] 装配动态路由, %o', routes)
        const dynamicRoutes = formatCallback(routes, Layout)
        console.debug('[MetaElement] 动态路由数据为: %o', dynamicRoutes)
        router.addRoutes(dynamicRoutes)
    })
}

/**
 * 装载动态路由，如果系统存在静态配置的路由，则必须在route.onReady中调用此方法进行动态路由装配。否则由于异步路由数据可能导致动态路由还未装配，
 * 路由跳转已经发生，从而导致路由不存在。
 * @param router 必填项。VueRouter对象。
 * @param axios 必填项。此axios将承担起动态路由数据的异步获取职责
 * @param Layout 可选项，默认AdminLayout。布局组件。你可以利用插槽重写AdminLayout, 再进行传入。也可以不传，默认meta-element提供的AdminLayout。注意，如果传了此布局组件，
 *                  则此组件的name必须与动态路由数据中对应的由的component名称相同。否则由于动态路由数据是不约束层次结构的，系统并不知道这个Layout将装配到哪个
 *                  路由上。特别的是，meta路由一定会生效，而不受此条件限制。因为meta路由是固定的结构，系统知道Layout组件将应用在哪个路由上。
 * @param url 可选项, 默认为db-meta服务端动态路由数据接口地址。url告诉axios从哪个资源地址获取动态路由数据
 * @param formatCallback 可选项，默认装配规则。 动态路由数据获取后，将执行一个格式化动态路由数据的回调函数。例如，由于动态路由数据中的component没法是一个Vue组件数据，它可能只能是一个组件名，
 *                      那么这个回调函数中就得根据这个组件名找到对应的Vue组件实例，然后替换上去。你也可以做一些其他数据过滤和装配的事情。
 */
export default function (router, axios, Layout, url, formatCallback) {
    addMetaRoutes(router, Layout)
    addDynamicRoutes(router, axios, Layout, url, formatCallback)
}