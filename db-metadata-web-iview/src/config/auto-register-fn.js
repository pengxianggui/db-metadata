import axios from "../axios";
import utils from "../utils";

let RegisterGlobalFn = {};
RegisterGlobalFn.install = function (Vue, opts = {}) {

    // 注册全局方法
    Vue.prototype.$axios = axios(opts['axios']);    // {axios: {}} // 对axios进行配置, 如baseURL等
    Vue.prototype.$merge = utils.merge;
    Vue.prototype.$reverseMerge = utils.reverseMerge;
    Vue.prototype.$compile = utils.compile;
    Vue.prototype.$dialog = utils.dialog;

    let authorities = utils.assertEmpty(opts['authorities'], []);
    let upperAuthorities = authorities.filter(ele => utils.isString(ele)).map(ele => ele.toUpperCase());
    Vue.prototype.$hasAuth = function (permissions) {

        if (utils.isArray(permissions)) {
            return permissions.filter(ele => utils.isString(ele))
                .every(ele => upperAuthorities.indexOf(ele.toUpperCase()))
        }
        if (utils.isString(permissions)) {
            return upperAuthorities.indexOf(permissions.toUpperCase()) > -1;
        }
        return false;
    };
};
export default RegisterGlobalFn;