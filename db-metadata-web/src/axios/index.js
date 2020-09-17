import axios from 'axios'
import {e_format, s_format} from "./responseExchange"

let instance = axios.create({
    baseURL: '/meta'
});

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

export default instance