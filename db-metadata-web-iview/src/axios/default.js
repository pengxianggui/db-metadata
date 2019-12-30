import axios from 'axios'
import {s_format, e_format} from "./responseExchange";
import config from "./config";

let instance = axios.create(config);

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

export default instance