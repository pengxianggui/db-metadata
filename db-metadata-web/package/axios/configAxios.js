/**
 * 为axios扩展safeGet和safePost方法
 * @param axios
 */
import {isEmpty} from "../utils/common";

export default function (axios) {
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
    return axios
}
