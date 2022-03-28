import configAxios from "../axios/configAxios";
import utils from "../utils";
import {hasAuth, hasRole, hasAllAuth, hasAllRole, hasAnyAuth, hasAnyRole, isRoot} from "../access";
import * as TagViewUtil from "../layout/tagview/tag-method";

export default function (Vue, opts) {
    Vue.prototype.$axios = configAxios(opts)

    Vue.prototype.$merge = utils.merge
    Vue.prototype.$reverseMerge = utils.reverseMerge
    // 通过prototype传递Vue(这两个方法中是需要有对Vue的引用的), 避免打包后Vue找不到
    utils.merge.prototype.Vue = Vue
    utils.reverseMerge.prototype.Vue = Vue

    Vue.prototype.$compile = utils.compile
    Vue.prototype.$dialog = utils.createDialog(Vue) // 传递Vue
    Vue.prototype.$getGlobalComponents = () => { // 获取所有全局注册的组件
        return Vue.options.components;
    }

    Vue.prototype.$isRoot = isRoot
    Vue.prototype.$hasAnyRole = hasAnyRole
    Vue.prototype.$hasAllRole = hasAllRole
    Vue.prototype.$hasAnyAuth = hasAnyAuth
    Vue.prototype.$hasAllAuth = hasAllAuth
    Vue.prototype.$hasAuth = hasAuth
    Vue.prototype.$hasRole = hasRole

    /**
     * 封装了返回操作涉及的逻辑: 关闭当前TagView、关闭window
     *
     * @param router VueRouter对象， 若不传入，则取当前this.$router, 这种情况应当在Vue实例中调用
     */
    Vue.prototype.$goBack = function (router) {
        let {currentRoute} = utils.assertEmpty(router, this.$router)
        TagViewUtil.close(currentRoute).then(() => {
            this.$router.go(-1)
            const {meta: {newTab}} = currentRoute
            if (newTab) {
                window.close()
            }
        })
    }
}

