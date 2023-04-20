import {appConfig} from "./config";
import {setCookie, getCookie, deleteCookie} from "./utils/cookies";

const set = function (token) {
    const {tokenKey, tokenIn} = appConfig
    switch (tokenIn) {
        case 'cookies':
            setCookie(tokenKey, token)
            break;
        case 'localStorage':
        default:
            localStorage.setItem(tokenKey, token)
            break;
    }
}

const get = function () {
    const {tokenKey, tokenIn} = appConfig
    let tokenValue
    switch (tokenIn) {
        case 'cookies':
            tokenValue = getCookie(tokenKey)
            break;
        case 'localStorage':
        default:
            tokenValue = localStorage.getItem(tokenKey)
            break;
    }
    return tokenValue
}

const remove = function () {
    const {tokenKey, tokenIn} = appConfig
    switch (tokenIn) {
        case 'cookies':
            deleteCookie(tokenKey)
            break;
        case 'localStorage':
        default:
            localStorage.removeItem(tokenKey)
            break;
    }
}

const getTokenKey = function () {
    return appConfig.tokenKey
}

export default {
    set,
    get,
    remove,
    getTokenKey
}
