import axios from 'axios'
import caseAxios from './case'
import {BASE_URL, ERROR_MSG, SUCCESS_MSG} from '../constant/constant'

// import Qs from 'qs' // 用来处理参数，可不使用，若要使用，npm安装： npm install qs
axios.defaults.baseURL = BASE_URL;
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=utf-8';

const CancelToken = axios.CancelToken;
const source = CancelToken.source();

// 添加一个请求拦截器
axios.interceptors.request.use(config => {
        // config.headers.languagetype = 'CN' // 举例，加上一个公共头部
        // config.data = Qs.stringify(config.data) // 处理数据，可不写
        if (config.url.indexOf("{") > 0 || config.url.indexOf("}") > 0) { // 请求url中含有{或}表示有参数未填充, 取消请求
            config.cancelToken = source.token;
            source.cancel("request params not prepared, cancel this request..");
        }
        return config
    },
    err => {
        console.error("err:", err);
        return Promise.reject(err)
    });

// 响应拦截器
axios.interceptors.response.use(res => {
    let result = res.data;
    const msg = result.msg;
    result.msg = msg ? msg : SUCCESS_MSG;

    if (result && result.state !== "ok") {
        res.msg = msg || ERROR_MSG;
        console.error("res.msg:", res.msg, "res", res);
        return Promise.reject(res)
    }
    return Promise.resolve(result);
}, err => {
    err.msg = err.toString();
    console.error("[ERROR] ", err);
    return Promise.reject(err)
});

// 特殊的get/post, case.js中定义有特殊逻辑需求的请求/响应拦截器
axios.$caseGet = function (url, config) {
    return caseAxios.get(url, config);
};
axios.$casePost = function (url, data, config) {
    return caseAxios.post(url, data, config);
};

export default axios
