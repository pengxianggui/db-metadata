import MetaLayout from "../layout/MetaLayout";
import MetaMain from '../layout/admin'
import {access, detect, getToken, hasAuth, hasRole, setRoles} from "../access";

import errorRoutes from './data/error'
import assembleMetaRoute from './data/meta'
import assembleDynamicRoute from './data/dynamic'
import {routeUrl} from "../constant/url";

// 路由数据注册
function registerRouteData(Vue, opts) {
    const {axios, router, layout: Layout = MetaLayout} = opts

    const routes = [] // 最终的路由数据
    const routesInLayout = {
        path: '/',
        name: 'MetaLayout',
        hidden: true,
        component: Layout,
        children: []
    }

    router.onReady(() => {
        // 组装平台维护相关路由
        const metaRoute = assembleMetaRoute(MetaMain)
        routesInLayout.children.push(...metaRoute)

        // 组装动态路由
        assembleDynamicRoute(axios).then((dynamicRoutes) => {
            routesInLayout.children.push(...dynamicRoutes)
        }).catch(err => {
            console.error('动态路由装配发生错误: ' + err)
        }).finally(() => {
            routes.push(routesInLayout) // 添加在Layout布局下的路由
            routes.push(...errorRoutes) // 添加外层路由(不过Layout)

            router.addRoutes(routes)
        })
    })
}

/**
 * 判定是否有路由权限，若无，则前往401，若有，则放行
 * @param to
 * @param next
 */
const permit = function (to, next) {
    const {
        meta: {
            need_permit = true,
            permit_by = 'auth',
            auths = [],
            roles = [],
            auth_match_mode = 'any',
            role_match_mode = 'any'
        } = {}
    } = to

    if (!need_permit) { // 不需要鉴权
        next()
    } else {
        if ((permit_by === 'role' && hasRole(roles, role_match_mode))
            || (permit_by === 'auth' && hasAuth(auths, auth_match_mode))) { // 有权限
            next()
        } else { // 无权限
            next(routeUrl.R_401)
        }
    }
}

// 路由拦截器注册
function registerInterceptor(Vue, opts) {
    const {router, routerInterceptor: {enable = true} = {}} = opts
    if (enable) {
        router.beforeEach(async (to, from, next) => {
            const {meta: {need_permit = true} = {}} = to

            if (need_permit) {
                await detect(Vue) // 检测用户信息并设置
            }
            permit(to, next)
        })
    }
}

function registerRouter(Vue, ops) {
    registerRouteData(Vue, ops); // 注册路由数据
    registerInterceptor(Vue, ops) // 注册路由守卫
}

export default {
    registerRouter
}
