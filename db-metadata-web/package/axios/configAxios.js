/**
 * 为axios扩展safeGet和safePost方法
 * @param axios
 */
import {isEmpty} from "../utils/common";
import {Message} from "element-ui";
import {appConfig} from "../config";
import {routeUrl} from "../constant/url";
import {clearUser, getToken} from "../access";

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
        const {state, msg: message, code} = res.data
        if (state !== 'ok') {
            Message({
                message: message,
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
        Message({
            message: err.message,
            type: "error"
        })
        return Promise.reject(err)
    });
}

export default function (router, axios) {
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
        if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
            // return Promise.cancel('url: ' + url + ' 未编译 ...');
            console.warn('url: ' + url + ' 未编译 ...')
            return new Promise(resolve => {
            }, reject => {
            })
        }
        let compileUrl = resolve(url, config);
        return axios.get(compileUrl, config);
    }
    axios.safePost = function (url, data, config = {}) {
        if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
            console.warn('url: ' + url + ' 未编译 ...')
            return new Promise(resolve => {
            }, reject => {
            })
        }
        let compileUrl = resolve(url, config);
        return axios.post(compileUrl, data, config);
    }

    configInterceptor(router, axios)
    return axios
}
