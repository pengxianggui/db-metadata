/**
 * 为axios扩展safeGet和safePost方法
 * @param axios
 */
import Axios from 'axios'
import {isEmpty, assertEmpty, printWarn} from "../utils/common";
import {Message} from "element-ui";
import {appConfig} from "../config";
import {routeUrl} from "../constant/url";
import {clearUser, getToken} from "../access";
import {validUrlCompiled, validParamsCompiled} from "../utils/url";

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
    const {router, axios: axiosConfig} = opts

    const axios = Axios.create(axiosConfig)

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

        if (!validUrlCompiled(url) || !validParamsCompiled(params)) {
            console.warn('url: ' + url + ' 未编译 ...')
            const paramsStr = JSON.stringify(params)
            printWarn(`请求含有未编译内容, url: ${url}, params: ${paramsStr}`)
            return new Promise(((resolve, reject) => {
            }))
        }
        let compileUrl = resolve(url, config);
        return axios.get(compileUrl, config);
    }
    axios.safePost = function (url, data, config = {}) {
        const {params = {}} = config
        if (!validUrlCompiled(url) || !validParamsCompiled(params)) {
            console.warn('url: ' + url + ' 未编译 ...')
            const paramsStr = JSON.stringify(params)
            printWarn(`请求含有未编译内容, url: ${url}, params: ${paramsStr}`)
            return new Promise(((resolve, reject) => {
            }))
        }
        let compileUrl = resolve(url, config);
        return axios.post(compileUrl, data, config);
    }

    // 配置拦截器
    configInterceptor(router, axios)

    return axios
}
