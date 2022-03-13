/**
 * 为axios扩展safeGet和safePost方法
 * @param axios
 */
import {isEmpty, assertEmpty, isObject, isString, printWarn} from "../utils/common";
import {Message} from "element-ui";
import {appConfig} from "../config";
import {routeUrl} from "../constant/url";
import {clearUser, getToken} from "../access";


/**
 * 判断字符串是否未编译。比如下面这个未编译的字符串:
 *
 * "/table/list?objectCode={object_code}"
 *
 * 其中{object_code}就是未编译的部分。
 *
 * 字符串中同时含有"{"和"}"就视为未编译
 *
 * @param params
 * @returns {boolean}
 */
const strNotCompile = function (value) {
    if (!isString(value)) {
        return false
    }
    return value.indexOf("{") > -1 && value.indexOf("}") > -1
}
/**
 * 判断对象中的value是否含有未编译的值。比如下面这个未编译的对象:
 * <pre>
 *     {
 *         "key1": "{key1}",
 *         "key2": "value2"
 *     }
 * </pre>
 * 其中key1的值就是未编译的。
 *
 * @param params
 * @returns {boolean}
 */
const paramsNotCompile = function (params = {}) {
    if (!isObject(params)) {
        return false
    }
    for (let key in params) {
        let value = params[key]
        let valueNotCompile = (isObject(value) ? paramsNotCompile(value) : strNotCompile(value))
        if (valueNotCompile) {
            return true
        }
    }
    return false
}

/**
 * 配置axios默认的拦截器
 * @param axios
 */
const configInterceptor = function (router, axios) {
    // 添加一个请求拦截器
    axios.interceptors.request.use(config => {
            let token = getToken()
            if (!isEmpty(token)) {
                config.headers[appConfig.tokenKey] = getToken()
            }
            return config
        },
        err => {
            console.error("err:", err);
            return Promise.reject(err)
        });

    // 响应拦截器
    axios.interceptors.response.use(res => {
        const {state, msg, message, code} = res.data

        if (state !== 'ok' && code != 0) {
            Message({
                message: assertEmpty(msg, message),
                type: "error"
            })

            if (code === 401) { // 未认证
                clearUser()
                router.push(routeUrl.R_LOGIN)
            }
            if (code === 403) { // 无权限
                router.push({path: routeUrl.R_401, query: res.data})
            }
            return Promise.reject(res.data);
        }

        return Promise.resolve(res.data);
    }, err => {
        console.error("[ERROR] ", err);
        const {msg, message} = err
        Message({
            message: assertEmpty(msg, message),
            type: "error"
        })
        return Promise.reject(err)
    });
}

export default function (opts) {
    const {router, axios, axiosInterceptor: {enable = true} = {}} = opts

    if (isEmpty(axios)) {
        console.error('[MetaElement] 必须配置axios!请实例化axios并配置')
        return
    }

    /**
     * http://localhost:8080/meta/table/list?objectCode=meta_field?object_code=&fs=object_code,field_code,is_primary,en,cn,order_num,db_type,db_type_length,id&p=1&s=5
     * 兼容处理
     * @author: konbluesky
     * @date : 20210909
     */
    function resolve(url, config = {}) {
        if (url.indexOf("?") > -1) {
            const q = {};
            url.replace(/([^?&=]+)=([^&]*)/g, (_, k, v) => q[k] = v);
            config.params = Object.assign(config.params || {}, q);
            url = url.replace(/\?.*/g, "");
        }
        return url
    }

    axios.safeGet = function (url, config = {}) {
        const {params = {}} = config

        if (strNotCompile(url) || paramsNotCompile(params)) {
            console.warn('url: ' + url + ' 未编译 ...')
            const paramsStr = JSON.stringify(params)
            printWarn(`请求含有未编译内容, url: ${url}, params: ${paramsStr}`)
            return new Promise(((resolve, reject) => {}))
        }
        let compileUrl = resolve(url, config);
        return axios.get(compileUrl, config);
    }
    axios.safePost = function (url, data, config = {}) {
        const {params = {}} = config
        if (strNotCompile(url) || paramsNotCompile(params)) {
            console.warn('url: ' + url + ' 未编译 ...')
            const paramsStr = JSON.stringify(params)
            printWarn(`请求含有未编译内容, url: ${url}, params: ${paramsStr}`)
            return new Promise(((resolve, reject) => {}))
        }
        let compileUrl = resolve(url, config);
        return axios.post(compileUrl, data, config);
    }

    if (enable) {
        configInterceptor(router, axios)
    }
    return axios
}
