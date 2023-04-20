import {isArray, isString, isEmpty, randomInt} from "./utils/common";
import utils from './utils'
import {appConfig} from "./config";
import {restUrl} from "./constant/url";
import Cache from "./constant/cacheKey";
import Token from "./token";

/**
 * 访问控制
 * @type {{root: string, roles: []}}
 */
export const access = {
    root: '0', // ROOT用户的用户id。为0表示是ROOT用户, 是DbMeta内置的用户，ROOT用户拥有所有权限, 即使没有绑定任何角色、权限。是DbMeta初始化时内置的用户
    // 当前用户
    user: {
        id: null,
        username: null,
        avatar: 'avatar' + randomInt(1, 14),
        roles: [],
        auths: [],
        root: false,
        attrs: {}
    }
}

/**
 * 检测缓存的用户信息，若不存在，则重新取之。若存在，则do nothing
 * @returns {Promise<void>}
 */
export const detect = function (Vue) {
    return new Promise((resolve, reject) => {
        const token = Token.get()
        const userId = access.user.id

        if (!isEmpty(token) && isEmpty(userId)) { // 有token但无用户信息则和服务端同步一次用户信息。 id有值时表示缓存中存在用户信息，(如果是刷新浏览器，用户信息会丢失，就需要从token重新取用户信息了)
            let headers = {}
            headers[appConfig.tokenKey] = Token.get()
            Vue.prototype.$axios.safeGet(restUrl.LOGIN_INFO, {
                headers: headers
            }).then(({data}) => {
                setUser(data)
                resolve(data)
            }).catch(err => {
                clearUser()
                reject(err)
            });
        } else {
            resolve()
        }
    })
}

/**
 * 判断当前登陆用户是否为ROOT用户, 大小写敏感.
 * 对内使用
 * @returns {boolean}
 */
export function isRoot() {
    return access.user.root
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
    const {enableCertification} = appConfig
    if (!enableCertification) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若为ROOT用户
        return true;
    }

    const {roles: userRoles} = access.user
    if (isEmpty(needRoles)) {
        return true
    }

    if (!Array.isArray(userRoles)) {
        return false
    }

    if (isString(needRoles)) { // 视为英文逗号分隔的角色编码字符串
        needRoles = needRoles.split(',')
    }

    return needRoles.some(r => userRoles.includes(r)) // route中定义的roles只要有一个符合即可
}

/**
 * Use meta.role to determine if the current user has permission
 * @param userRoles
 * @param route
 */
export function hasAnyAuth(needAuths) {
    const {enableCertification} = appConfig
    if (!enableCertification) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若为ROOT用户
        return true;
    }

    const {auths: userAuths} = access.user
    if (isEmpty(needAuths)) {
        return true
    }

    if (!Array.isArray(userAuths)) {
        return false
    }

    if (isString(needAuths)) {
        needAuths = needAuths.split(',')
    }

    return needAuths.some(r => userAuths.includes(r)) // route中定义的auths只要有一个符合即可
}

/**
 * Use meta.role to determine if the current user has permission
 * @param userRoles
 * @param route
 */
export function hasAllRole(needRoles) {
    const {enableCertification} = appConfig
    if (!enableCertification) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若为ROOT用户
        return true;
    }

    const {roles: userRoles} = access.user
    if (isEmpty(needRoles)) {
        return true
    }

    if (!Array.isArray(userRoles)) {
        return false
    }

    if (isString(needRoles)) {
        needRoles = needRoles.split(',')
    }

    return needRoles.every(r => userRoles.includes(r)) // route中定义的roles只要有一个符合即可
}

/**
 * Use meta.role to determine if the current user has permission
 * @param userRoles
 * @param route
 */
export function hasAllAuth(needAuths) {
    const {enableCertification} = appConfig
    if (!enableCertification) {
        return true; // 若未开启权限控制, 则一律视为有权限
    }
    if (isRoot()) { // 若为ROOT用户
        return true;
    }


    const {auths: userAuths} = access.user
    if (isEmpty(needAuths)) {
        return true
    }

    if (!Array.isArray(userAuths)) {
        return false
    }

    if (isString(needAuths)) {
        needAuths = needAuths.split(',')
    }

    return needAuths.every(r => userAuths.includes(r)) // route中定义的auths只要有一个符合即可
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

export function setUser(user) {
    if (utils.isEmpty(user)) {
        utils.clear(access.user)
    } else {
        utils.reverseMerge(access.user, user, true, true)
    }
}

export function getUser() {
    return access.user
}

export function getRoles() {
    return access.user.roles
}

export function getAuths() {
    return access.user.auths
}

/**
 * 清除用户信息和token
 */
export function clearUser() {
    setUser({})
    Token.remove()
    Cache.clear()
}

export default {
    isRoot,
    hasAnyRole,
    hasAllRole,
    hasAnyAuth,
    hasAllAuth,
    setUser,
    getUser,
    clearUser
}
