import utils from "./utils";
import {isArray, isString} from "./utils/common";


/**
 * 访问控制
 * @type {{root: string, roles: []}}
 */
export const access = {
    root: 'ROOT', // 只有ROOT角色的用户才能访问元数据快捷编辑、平台维护模块
    roles: [] // 当前用户角色
}

/**
 * 判断当前登陆角色是否为ROOT角色, 大小写敏感.
 * 对内使用
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
    if (!isString(roles) && !isArray(roles)) {
        throw new Error('[MetaElement] the user roles must be a Array or a String!');
    }

    access.roles = []
    if (isArray(roles)) {
        access.roles.push(...roles)
    }

    if (isString(roles)) {
        access.roles.push(roles)
    }

    return access.roles;
}

export default {
    isRoot,
    setRoles
}