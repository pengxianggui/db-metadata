import axios from "axios";
import config from './config'
import {e_format, s_format} from "./responseExchange";

let caseAxios = axios.create(config);

caseAxios.interceptors.response.use(res => {
    if (!s_format(res)) {
        return Promise.reject(res.data);
    }
    // 判断响应数据中是否含有反斜杠, 含有则reject
    let rex = new RegExp("\\\\", "g");
    if (res.data.toString().match(rex)) {
        return Promise.reject(res.data);
    }
    return Promise.resolve(res.data);
}, err => {
    e_format(err);
    console.error("[ERROR] ", err);
    return Promise.reject(err);
});

export default caseAxios;