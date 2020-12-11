import configAxios from "../axios/configAxios";
import utils from "../utils";
import user from "../access";
import {TagViewUtil} from "../index";

export default function (Vue, opts) {
    Vue.prototype.$axios = configAxios(opts.axios)
    Vue.prototype.$merge = utils.merge
    Vue.prototype.$reverseMerge = utils.reverseMerge
    Vue.prototype.$compile = utils.compile
    Vue.prototype.$dialog = utils.dialog
    Vue.prototype.$isRoot = user.isRoot
    Vue.prototype.$hasRoles = user.hasRoles

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

