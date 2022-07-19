import {isEmpty, convertToObject, isObject, isArray, isBoolean, printErr, printWarn} from "../utils/common";
import Pages from '../page/index'
import Tmpls from '../template/index'
import MetaLayout from "../layout/MetaLayout";
import {merge} from "../utils/merge";

/**
 * 需要被PageSelector选中的组件都装配到这里
 * @type {*[]}
 */
export const components = [
    ...Pages,
    ...Tmpls,
    MetaLayout
]

/**
 * 将动态路由需要转换的组件过滤缓存下来
 * @param Vue
 * @param opts
 */
const retainComponents = function (Vue, opts) {
    const {components: componentsInOpts = []} = opts
    if (!isArray(componentsInOpts)) {
        throw new Error('[meta-element] components配置项必须是组件数组！')
    }

    components.push(...componentsInOpts)
}

const getComponent404 = function (Vue, componentName) {
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
export const exchangeComponent = function (Vue, componentName) {
    for (let i = 0; i < components.length; i++) {
        let c = components[i]
        if (c.name === componentName) {
            return c
        }
    }

    return getComponent404(Vue, componentName)
}

const includes = ['path', 'name', 'component', 'children', 'meta', 'redirect', 'props'] // 滤出的字段

/**
 * 将props值转为VueRouter标准值(布尔/对象/函数)
 * @param propsValue
 * @returns {boolean|any}
 */
const exchangeProps = function (propsValue) {
    if (isEmpty(propsValue)) {
        return false; // props: false
    }

    try {
        const propsJson = JSON.parse(propsValue)
        const {type, value} = propsJson
        switch (type) {
            case 'boolean':
                if (!isBoolean(value)) {
                    throw new Error('动态路由路由数据异常, type为boolean, 值非boolean类型')
                }
                return value;
            case 'object':
                return JSON.parse(value)
            case 'function':
                return eval(value)
            default:
                return false;
        }
    } catch (error) { // 解析失败, 发生异常, 都设为false, false也是VueRouter中props的默认值
        printWarn(error.message)
        return false; // props: false
    }
}

/**
 * 转换route数据中的kv
 * @param route 最终的route
 * @param r 路由数据
 * @param k 路由数据中的key
 */
const exchangeRouteKey = function (route, r, k) {
    let value;
    switch (k) {
        case 'meta':
            const {
                cn: title = 'No-Name',
                disable = false,
                order = 0,
                need_permit,
                noCache = false,
                permit_by = 'auth',
                auths = [],
                roles = [],
                auth_match_mode = 'any',
                role_match_mode = 'any'
            } = r // 解构出来的这些字段是直接存储在表字段里的，而不是meta, 为了更好的表单交互体验。但是路由数据扩展的字段，需要挪到meta里，所以这里提取下，放到meta字段中

            value = {
                title: title,
                disable: disable,
                order: order,
                need_permit: need_permit,
                noCache: noCache,
                permit_by: permit_by,
                auths: auths,
                roles: roles,
                auth_match_mode: auth_match_mode,
                role_match_mode: role_match_mode
            }
            merge(value, convertToObject(r[k]))
            break;
        case 'props':
            value = exchangeProps(r[k])
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

const exchange = function (Vue, routes) {
    return routes.map(r => {
        if (r.hasOwnProperty("children")) {
            r.children = exchange(Vue, r.children)
        }
        const route = {}

        Object.keys(r).filter(k => includes.indexOf(k) > -1).filter(k => {
            return !isEmpty(r[k]) && r[k] !== 'null' // TODO 路由数据中redirect值可能非返回"null"字符串
        }).forEach(k => {
            exchangeRouteKey(route, r, k)
        })

        const {component: componentName} = route;
        route.component = exchangeComponent(Vue, componentName)

        storageRouteId(route, r.id)

        return route
    })
}

/**
 * 将路由数据中的component名全部替换为component组件
 * @param routes
 * @returns {*}
 */
export default function (Vue, opts, routes) {
    retainComponents(Vue, opts)

    routes = exchange(Vue, routes)
    return routes // 返回完整装配后的路由
}
