import {restUrl} from "./constant/url";
import utils from "./utils";
import {clearUser} from "./access";
import {printErr} from "./utils/common";

export const elementVersion = '2.12.0';

export const pageNumArea = [5, 10, 20, 50, 100, 200];

export const defaultPrimaryKey = 'id';

/**
 * 功能模板中, 当两个元对象通过另一个中间表进行关联时, 传递主键的key值
 * @type {string}
 */
export const relatedId = '_relate_id'

/**
 * 系统配置
 * @type {{}}
 */
export const appConfig = {
    name: 'DB-Metadata低代码开发工具————Data drive everything',
    logo: null,
    registerable: true,
    addable: true,
    enableCertification: true,
    devMode: false,
    tokenKey: 'X-TOKEN',
    showGreeting: true,
    showThemeSetting: true,
    allowCustomTheme: true, // 是否允许自由定义
}

export const configApp = function (Vue, opts = {}) {
    const axios = Vue.prototype.$axios
    if (!axios) {
        printErr('axios必须配置')
    } else {
        axios.get(restUrl.GET_APP_CONFIG).then(({data}) => {
            const {enableLogin} = data
            if (enableLogin === false) {
                clearUser() // 防止后端禁用登录后，前端仍有缓存
            }
            utils.reverseMerge(appConfig, data)
        }).catch(err => {
            printErr(err)
        })
    }
}
