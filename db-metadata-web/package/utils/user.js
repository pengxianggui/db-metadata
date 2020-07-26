import utils from "./index";
import {access} from "../constant/variable";
import {isArray, isString} from "./common";

/**
 * 判断当前登陆角色是否为ROOT角色, 大小写敏感
 * @returns {boolean}
 */
export function isRoot() { // 根据角色鉴权
    const root = access.root;
    let userRoles = utils.assertEmpty(access.roles, []); // 用户拥有的角色

    if (utils.isArray(userRoles)) {
        return userRoles.indexOf(root) > -1
    }

    if (utils.isString(userRoles)) {
        return userRoles === root
    }

    console.error(`[MetaElement] 用户角色 ${userRoles} 非法, 必须为字符数组或字符串`)
    return false;
}

/**
 * 设置用户角色
 * @param roles
 * @returns {[]}
 */
export function setRoles(roles = []) {
    if (!isArray(roles)) {
        throw new Error('[MetaElement] the user roles must be a Array(composed by String value)!');
    }

    access.roles = roles;
    return access.roles;
}