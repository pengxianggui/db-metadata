import {restUrl} from "./constant/url";
import utils from "./utils";
import {clearUser} from "./access";
import {printErr} from "./utils/common";

export const elementVersion = '2.12.0';

export const pageNumArea = [5, 10, 20, 50, 100, 200];

export const defaultPrimaryKey = 'id';

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
    tokenIn: 'localStorage',
    loginKey: 'username',
    pwdKey: 'password',
    showGreeting: true,
    showThemeSetting: true,
    allowCustomTheme: true, // 是否允许自由定义
    loginBg: null,
    docUrl: null
}

export const configApp = function (Vue, opts = {}) {
    const {axios: {baseURL}} = opts

    // 同步请求
    let request = new XMLHttpRequest();
    request.open('GET', baseURL + restUrl.GET_APP_CONFIG, false);
    request.send(null);
    if (request.status === 200) {
        const res = JSON.parse(request.responseText)
        const {state, code, data, message, msg} = res

        if (state !== 'ok' && code !== 0) {
            utils.printErr('系统配置信息获取失败！将采用默认的系统配置: %s, 错误信息: %s',
                JSON.stringify(appConfig),
                utils.assertEmpty(message, msg))
        } else {
            utils.reverseMerge(appConfig, data, true, true)
        }

    } else { // 系统配置失败，则系统不可用
        utils.printErr('系统配置信息获取失败！将采用默认的系统配置: %s, 错误码: %s',
            JSON.stringify(appConfig),
            request.status)
    }

    if (appConfig.enableCertification === false) {
        clearUser()  // 防止后端禁用登录后，前端仍有缓存
    }
}
