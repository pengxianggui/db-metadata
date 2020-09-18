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
    const {state} = res.data
    if (state !== 'ok') {
        return Promise.reject(res.data);
    }
    return Promise.resolve(res.data);
}, err => {
    console.error("[ERROR] ", err);
    return Promise.reject(err)
});

export default instance