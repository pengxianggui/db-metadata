import utils from "./utils";
import {isArray, isString, isEmpty} from "./utils/common";
import {appConfig} from "./config";
import {restUrl} from "./constant/url";

/**
 * 访问控制
 * @type {{root: string, roles: []}}
 */
export const access = {
    root: 'ROOT', // ROOT角色编码。ROOT角色是特殊的角色，DbMeta内置的平台维护模块，只要拥有ROOT角色即可访问，无论ROOT是否绑定权限
    // 当前用户
    user: {
        username: '',
        roles: [],
        auths: []
    }
}

/**
 * 检测缓存的用户信息，若不存在，则重新取之。若存在，则do nothing
 * @returns {Promise<void>}
 */
export const detect = async function (Vue) {
    if (!isEmpty(getToken()) && isEmpty(getRoles())) {
        let headers = {}
        headers[appConfig.tokenKey] = getToken()
        Vue.prototype.$axios.safeGet(restUrl.LOGIN_INFO, {
            headers: headers
        }).then(({data}) => {
            setUser(data)
        })
    }
}

/**
 * 获取当前登录用户前端缓存的TOKEN
 */
export const getToken = function () {
    return localStorage.getItem(appConfig.tokenKey);
}
/**
 * 缓存token
 * @param token
 */
export const setToken = function (token) {
    localStorage.setItem(appConfig.tokenKey, token)
}

/**
 * 判断当前登陆角色是否为ROOT角色, 大小写敏感.
 * 对内使用
 * @returns {boolean}
 */
export function isRoot() { // 根据角色鉴权
    const root = access.root;
    let userRoles = utils.assertEmpty(access.user.roles, []); // 用户拥有的角色

    if (utils.isArray(userRoles)) {
        return userRoles.indexOf(root) > -1
    }

    if (utils.isString(userRoles)) {
        return userRoles === root
    }

    console.error(`[MetaElement] 用户角色 ${userRoles} 非法, 必须为字符数组或字符串`)
    return false;
}

export function hasRole(needRoles, mode = 'any') {
    if (mode.toLowerCase() === 'any') {
        return hasAnyRole(needRoles)
    } else {
        return hasAllRole(needRoles)
    }
}

export function hasAuth(needAuths, mode = 'any') {
    if (mode.toLowerCase() === 'any') {
        return hasAnyAuth(needAuths)
    } else {
        return hasAllAuth(needAuths)
    }
}

/**
 * Use meta.role to determine if the current user has permission
 * @param userRoles
 * @param route
 */
export function hasAnyRole(needRoles) {
    const {enableAuth} = appConfig
    if (!enableAuth) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若拥有ROOT角色，则视为有权限
        return true;
    }

    const {roles: userRoles} = access.user
    if (isEmpty(needRoles)) {
        return true
    }
    if (!Array.isArray(userRoles)) {
        return false
    }

    const upperCaseNeedRoles = needRoles.map(r => r.toUpperCase()) // 大小写不敏感
    return upperCaseNeedRoles.some(r => userRoles.includes(r.toUpperCase())) // route中定义的roles只要有一个符合即可
}

/**
 * Use meta.role to determine if the current user has permission
 * @param userRoles
 * @param route
 */
export function hasAnyAuth(needAuths) {
    const {enableAuth} = appConfig
    if (!enableAuth) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若拥有ROOT角色，则视为有权限
        return true;
    }


    const {auths: userAuths} = access.user
    if (isEmpty(needAuths)) {
        return true
    }
    if (!Array.isArray(userAuths)) {
        return false
    }

    const upperCaseNeedAuths = needAuths.map(r => r.toUpperCase()) // 大小写不敏感
    return upperCaseNeedAuths.some(r => userAuths.includes(r.toUpperCase())) // route中定义的auths只要有一个符合即可
}

/**
 * Use meta.role to determine if the current user has permission
 * @param userRoles
 * @param route
 */
export function hasAllRole(needRoles) {
    const {enableAuth} = appConfig
    if (!enableAuth) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若拥有ROOT角色，则视为有权限
        return true;
    }


    const {roles: userRoles} = access.user
    if (isEmpty(needRoles)) {
        return true
    }
    if (!Array.isArray(userRoles)) {
        return false
    }

    const upperCaseNeedRoles = needRoles.map(r => r.toUpperCase()) // 大小写不敏感
    return upperCaseNeedRoles.every(r => userRoles.includes(r.toUpperCase())) // route中定义的roles只要有一个符合即可
}

/**
 * Use meta.role to determine if the current user has permission
 * @param userRoles
 * @param route
 */
export function hasAllAuth(needAuths) {
    const {enableAuth} = appConfig
    if (!enableAuth) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若拥有ROOT角色，则视为有权限
        return true;
    }


    const {auths: userAuths} = access.user
    if (isEmpty(needAuths)) {
        return true
    }
    debugger
    if (!Array.isArray(userAuths)) {
        return false
    }

    const upperCaseNeedAuths = needAuths.map(r => r.toUpperCase()) // 大小写不敏感
    return upperCaseNeedAuths.every(r => userAuths.includes(r.toUpperCase())) // route中定义的auths只要有一个符合即可
}

/**
 * 设置用户角色
 * @param roles
 * @returns {[]}
 */
export function setRoles(roles = []) {
    if (!isString(roles) && !isArray(roles)) {
        throw new Error('[MetaElement] the user roles must be a Array or a String!');
    }

    access.user.roles = []
    if (isArray(roles)) {
        access.user.roles.push(...roles)
    }

    if (isString(roles)) {
        access.user.roles.push(roles)
    }

    return access.user.roles;
}

/**
 * 设置用户权限
 * @param auths
 * @returns {[]}
 */
export function setAuths(auths = []) {
    if (!isString(auths) && !isArray(auths)) {
        throw new Error('[MetaElement] the user auths must be a Array or a String!');
    }

    access.user.auths = []
    if (isArray(auths)) {
        access.user.auths.push(...auths)
    }

    if (isString(auths)) {
        access.user.auths.push(auths)
    }

    return access.user.auths;
}

export function setUser({username = '', roles = [], auths = []}) {
    access.user.username = username;
    setRoles(roles)
    setAuths(auths)
}

export function getRoles() {
    return access.user.roles
}

export function getAuths() {
    return access.user.auths
}

export default {
    isRoot,
    hasAnyRole,
    hasAllRole,
    hasAnyAuth,
    hasAllAuth,
    getRoles,
    getAuths,
    setUser
}
