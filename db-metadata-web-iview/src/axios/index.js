import axios from 'axios'
import qs from 'qs'

// import Qs from 'qs' // 用来处理参数，可不使用，若要使用，npm安装： npm install qs
axios.defaults.baseURL = '/'; // 请求的默认域名
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=utf-8';

// 添加一个请求拦截器
axios.interceptors.request.use(config => {
        // config.headers.languagetype = 'CN' // 举例，加上一个公共头部
        // config.data = Qs.stringify(config.data) // 处理数据，可不写
        if (config.method === 'post') {
            config.data = qs.stringify(config.data)
        }
        return config
    },
    err => {
        return Promise.reject(err)
    });
// 响应拦截器
axios.interceptors.response.use(res => {
    if (res.data && res.data.state !== "ok") {
        return Promise.reject(res)
    }
    return res.data
}, err => {
    // Vue.prototype.$message.error(err)
    return Promise.reject(err)
});

export default axios
