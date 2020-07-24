import axios from "@/axios";
import utils from "../utils";

let RegisterGlobalFn = {};
import {access} from "../constant/variable";

RegisterGlobalFn.install = function (Vue, opts = {}) {

    // 注册全局方法
    Vue.prototype.$axios = axios(opts['axios']);    // {axios: {}} // 对axios进行配置, 如baseURL等
    Vue.prototype.$merge = utils.merge;
    Vue.prototype.$reverseMerge = utils.reverseMerge;
    Vue.prototype.$compile = utils.compile;
    Vue.prototype.$dialog = utils.dialog;

    Vue.prototype.$hasAllRole = function (roles) { // 根据角色鉴权
        console.log(access.roles)
        console.log(roles)
        let userRoles = utils.assertEmpty(access.roles, []); // 用户拥有的角色
        if (!utils.isArray(userRoles)) {
            console.error(`[MetaElement] 当前用户角色必须是数组或角色字符串`)
            return false;
        }

        if (utils.isArray(roles)) {
            return roles.filter(ele => utils.isString(ele))
                .every(ele => userRoles.indexOf(ele))
        }
        if (utils.isString(roles)) {
            return userRoles.indexOf(roles) > -1;
        }
        console.error(`[MetaElement] 角色参数${roles}非法, 必须为字符数组或字符串`)
        return false;
    };
};
export default RegisterGlobalFn;
