import axios from "axios";
import {BASE_URL} from '../constant'

let caseAxios = axios.create({
    baseURL: BASE_URL
});
caseAxios.interceptors.response.use(res => {
    return Promise.resolve(res);
}, err => {
    return Promise.reject(err);
});

export default caseAxios;