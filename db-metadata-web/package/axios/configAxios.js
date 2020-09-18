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
    axios.safeGet = function (url, config) {
        if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
            // return Promise.cancel('url: ' + url + ' 未编译 ...');
            console.warn('url: ' + url + ' 未编译 ...')
            return new Promise(resolve => {
            }, reject => {
            })
        }
        return axios.get(url, config);
    }
    axios.safePost = function (url, data, config) {
        if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
            console.warn('url: ' + url + ' 未编译 ...')
            return new Promise(resolve => {
            }, reject => {
            })
        }
        return axios.post(url, data, config);
    }
    return axios
}