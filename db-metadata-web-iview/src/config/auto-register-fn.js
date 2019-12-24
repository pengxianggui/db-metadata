import Vue from "vue";
import axios from "../axios";
import utils from "../utils";

const authorities = ['ADMIN'];  // pxg_todo 模拟的权限
// 注册全局方法
Vue.prototype.$axios = axios;
Vue.prototype.$merge = utils.merge;
Vue.prototype.$reverseMerge = utils.reverseMerge;
Vue.prototype.$compile = utils.compile;
Vue.prototype.$dialog = utils.dialog;

Vue.prototype.$hasAuth = function (permissions) {
    if (utils.isArray(permissions)) {
        return permissions.every(ele => authorities.indexOf(ele))
    }
    if (utils.isString(permissions)) {
        return authorities.indexOf(permissions) > -1;
    }
    return false;
};