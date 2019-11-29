import Vue from "vue";
import axios from "../axios";
import utils from "../utils";

// 注册全局方法
Vue.prototype.$axios = axios;
Vue.prototype.$merge = utils.merge;
Vue.prototype.$reverseMerge = utils.reverseMerge;
Vue.prototype.$compile = utils.compile;
Vue.prototype.$dialog = utils.dialog;