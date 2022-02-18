import axios from 'axios'
import {Message} from "element-ui";
import router from "@/router";

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
    const {state, msg: message, code} = res.data
    if (state !== 'ok') {
        Message({
            message: message,
            type: "error"
        })

        if (code === 401) {
            router.push('/login')
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

export default instance
