import MetaLayout from "../layout/MetaLayout";
import {access, clearUser, detect, hasAuth, hasRole} from "../access";
import Token from "../token";

import systemRoutes from './data/system'
import assembleMetaRoute from './data/meta'
import assembleDynamicRoute from './data/dynamic'
import {routeUrl} from "../constant/url";
import {assert, isArray, isBoolean, isEmpty, isFunction, isString} from "../utils/common";
import {appConfig} from "../config";

/**
 * 最终处理路由。并返回处理的结果。
 *
 * 过滤掉disable的路由
 * @param routes
 */
function dealRoutes(routes) {
    let routeList = []
    routes.forEach(r => {
        // 剔除所有disable的路由
        const {meta: {disable = false} = {}} = r
        if (disable !== true) {
            routeList.push(r)

            if (r.hasOwnProperty('children') && !isEmpty(r.children)) {
                r.children = dealRoutes(r.children)
            }
        }
    })
    return routeList
}

// 路由数据注册
function registerRouteData(Vue, opts) {
    const {router, layout: Layout = MetaLayout} = opts
    const axios = Vue.prototype.$axios

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
        const metaRoute = assembleMetaRoute()
        routesInLayout.children.push(...metaRoute)

        // 组装动态路由
        assembleDynamicRoute(Vue, axios, opts).then((dynamicRoutes) => {
            routes.push(...dynamicRoutes)
        }).catch(err => {
            console.error('动态路由装配发生错误: ' + err)
        }).finally(() => {
            routes.push(routesInLayout) // 添加在Layout布局下的路由
            routes.push(...systemRoutes) // 添加外层路由(不过Layout)
            router.addRoutes(dealRoutes(routes))
            // 参见 https://github.com/vuejs/vue-router/issues/1859 通过router.addRoutes添加的路由，router.options.routes中
            // 不会有，这导致了TagView中初始化的一些问题。因此这里的解决办法参考一哥们的: https://www.cnblogs.com/blueroses/p/7767285.html
            // 还存在一些问题，可能重复固定tag
        })
    })
}

/**
 * 路由上的 角色/权限 编码配置不一定是 字符串数组，还支持一个返回字符串数组的函数。函数入参为(to, from, next)， 路由钩子参数
 * @param rolesOrAuths
 */
const parseRolesOrAuth = function (rolesOrAuths, to, from, next) {
    if (isFunction(rolesOrAuths)) {
        const arr = rolesOrAuths(to, from ,next);
        assert(isArray(arr), `[MetaElement] 当路由参数meta.roles(或meta.auths)为函数时, 返回值必须为字符串数组。其含义为角色编码数组(或权限编码数组)`)
        return arr;
    }
    return rolesOrAuths
}

/**
 * 判定是否有路由权限，若无，则前往401，若有，则放行
 * @param to
 * @param next
 */
const permit = function (to, from, next) {
    let {
        path,
        meta: {
            need_permit,
            permit_by = 'auth',
            auths = [],
            roles = [],
            auth_match_mode = 'any',
            role_match_mode = 'any'
        } = {}
    } = to

    if (!isBoolean(need_permit)) { // 未配置need_permit，则依据是否配置auths或roles，推导need_permit
        need_permit = (permit_by == 'auth' ? !isEmpty(auths) : (!isEmpty(roles)))
    }

    const token = Token.get()
    if (path === routeUrl.R_LOGIN && !isEmpty(token)) { // 去登录页
        next('/')
    } else {
        if (!need_permit) { // 不需要鉴权
            next()
        } else { // 需要鉴权
            if (isEmpty(access.user.id)) { // 无用户信息
                clearUser()
                next(routeUrl.R_LOGIN)
            } else if ((permit_by === 'role' && hasRole(parseRolesOrAuth(roles, to, from, next), role_match_mode))
                || (permit_by === 'auth' && hasAuth(parseRolesOrAuth(auths, to, from, next), auth_match_mode))) { // 有权限
                next()
            } else { // 无权限
                next(routeUrl.R_401)
            }
        }
    }
}

// 路由拦截器注册
function registerInterceptor(Vue, opts) {
    const {router, routerInterceptor: {enable = true} = {}} = opts
    if (enable) {
        router.beforeEach(async (to, from, next) => {
            // 检测用户信息并设置; Promise完成后鉴权, 防止用户信息还没有拿到就鉴权, 肯定无权限了
            detect(Vue).finally(() => {
                // 启用认证, 此判断只能放到这里，如果放到registerInterceptor, 会导致appConfig异步请求和路由钩子执行时机的问题，导致编程路由刷新不会触发此钩子
                if (appConfig.enableCertification) {
                    permit(to, from, next)
                } else { // 未启用，直接放行
                    clearUser()
                    if (to.path === routeUrl.R_LOGIN) {
                        next('/')
                    } else {
                        next()
                    }
                }
            })
        })
    }
}

export default {
    registerRouteData,
    registerInterceptor
}
