import axios from 'axios'
import {e_format, s_format} from "./responseExchange";
import config from "./config";
import utils from '@/utils'

export default function (opts = {}) {
    utils.merge(opts, config);
    let instance = axios.create(opts);

    // 添加一个请求拦截器
    instance.interceptors.request.use(config => {
            return config
        },
        err => {
            console.error("err:", err);
            return Promise.reject(err)
        });

    // 响应拦截器
    instance.interceptors.response.use(res => {
        if (!s_format(res)) {
            return Promise.reject(res.data);
        }
        return Promise.resolve(res.data);
    }, err => {
        e_format(err);
        console.error("[ERROR] ", err);
        return Promise.reject(err)
    });


    // 扩展cancel ----------------------------------------------------------------------------------------------------------
    Promise.cancel = function (msg) {
        console.warn(msg);
        return new Promise(resolve => {
        }, reject => {
        })
    };
    Promise.prototype.cancel = function (onCancel) {
        onCancel();
        return this;
    };
// ---------------------------------------------------------------------------------------------------------------------

    // 安全请求, 如果url中含有未编译的占位变量, 则取消请求

    instance.safeGet = function (url, config) {
        if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
            return Promise.cancel('url: ' + url + ' 未编译 ...');
        }
        return instance.get(url, config);
    };

    instance.safePost = function (url, data, config) {
        if (url.indexOf("{") > 0 || url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
            return Promise.cancel('url: ' + url + ' 未编译 ...');
        }
        return instance.post(url, data, config);
    };

    return instance;
}
