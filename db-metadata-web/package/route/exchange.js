import Vue from 'vue'

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
const exchangeOne = function (componentName) {
    let components = Vue.options.components
    for (let componentsKey in components) {
        if (componentsKey === componentName) {
            return components[componentsKey]
        }
    }
    return getComponent404(componentName)
}

const exchangeAll = function (routes) {
    return routes.map(r => {
        if (r.hasOwnProperty("children")) {
            r.children = exchangeAll(r.children)
        }
        const {component: componentName} = r;
        r.component = exchangeOne(componentName)
        return r
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