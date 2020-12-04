import Vue from 'vue'
import {isEmpty, strToObject, isObject} from "../utils/common";

const getComponent404 = function (componentName) {
    return Vue.component("Component404", {
        template: `<h1>组件{{ componentName }}未找到, 请确保您全局注册了该组件</h1>`,
        data() {
            return {
                componentName: componentName
            }
        }
    })
}

/**
 * 根据组件名提取全局注册的组件
 * @param componentName
 */
export const exchangeComponent = function (componentName, Layout) {
    if (componentName === Layout.name) { // 布局组件支持直接传入, 避免约束应用必须全局注册(但是仍然需要路由数据中布局组件名和Layout.name保持一致)
        return Layout
    }

    let components = Vue.options.components
    for (let componentsKey in components) {
        if (componentsKey === componentName) {
            return components[componentsKey]
        }
    }
    return getComponent404(componentName)
}

const includes = ['path', 'name', 'component', 'children', 'meta', 'redirect'] // 滤出的字段

const exchangeRouteKey = function (route, r, k) {
    let value;
    switch (k) {
        case 'meta':
            value = strToObject(r[k])
            break;
        default:
            value = r[k]
    }
    route[k] = value
}

const storageRouteId = function (route, id) {
    if (!isEmpty(id) && !isEmpty(route)) {
        if (!isObject(route.meta)) {
            route.meta = {}
        }
        route.meta['id'] = id
    }
}

const exchangeAll = function (routes, Layout) {
    return routes.map(r => {
        if (r.hasOwnProperty("children")) {
            r.children = exchangeAll(r.children, Layout)
        }
        const route = {}
        Object.keys(r).filter(k => includes.indexOf(k) > -1).filter(k => {
            return !isEmpty(r[k]) && r[k] !== 'null' // TODO 路由数据中redirect值可能非返回"null"字符串
        }).forEach(k => {
            exchangeRouteKey(route, r, k)
        })

        const {component: componentName} = route;
        route.component = exchangeComponent(componentName, Layout)

        storageRouteId(route, r.id)

        return route
    })
}

/**
 * 将路由数据中的component名全部替换为component组件
 * @param routes
 * @returns {*}
 */
export default function (routes, Layout) {
    return exchangeAll(routes, Layout)
}