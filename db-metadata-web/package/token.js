import {appConfig} from "./config";

const set = function (token) {
    localStorage.setItem(appConfig.tokenKey, token)
}

const get = function () {
    return localStorage.getItem(appConfig.tokenKey)
}


export default {
    set,
    get
}
