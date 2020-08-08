import Vue from 'vue'
import {isEmpty} from "../utils/common";

const getComponent404 = function (componentName) {
    return Vue.component("Component404", {
        template: `<h1>组件{{componentName}}未找到, 请确保您全局注册了该组件</h1>`,
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
export const exchangeComponent = function (componentName) {
    let components = Vue.options.components
    for (let componentsKey in components) {
        if (componentsKey === componentName) {
            return components[componentsKey]
        }
    }
    return getComponent404(componentName)
}

const includes = ['path', 'name', 'component', 'children', 'meta', 'redirect'] // 滤出的字段

const exchangeAll = function (routes) {
    return routes.map(r => {
        if (r.hasOwnProperty("children")) {
            r.children = exchangeAll(r.children)
        }
        const route = {}
        Object.keys(r).filter(k => includes.indexOf(k) > -1).filter(k => {
            return !isEmpty(r[k]) && r[k] !== 'null' // TODO 路由数据中redirect值可能非返回"null"字符串
        }).map(k => {
            route[k] = r[k]
        })


        // TODO
        const {component: componentName} = route;
        route.component = exchangeComponent(componentName)
        return route
    })
}

/**
 * 将路由数据中的component名全部替换为component组件
 * @param routes
 * @returns {*}
 */
export default function (routes) {
    return exchangeAll(routes)
}