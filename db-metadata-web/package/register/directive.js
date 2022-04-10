import {hasAnyRole, hasAnyAuth, hasAllRole, hasAllAuth, hasAuth, hasRole} from "../access"
import {isObject, isString} from "../utils/common";

const permitAuthorize = function (authorize) {
    const {need_permit = false, permit_by = 'auth'} = authorize
    if (need_permit !== true) {
        return
    }

    let permit;
    if (permit_by === 'auth') {
        const {auths = [], auth_match_mode = 'any'} = authorize
        permit = hasAuth(auths, auth_match_mode)
    } else if (permit_by === 'role') {
        const {roles = [], role_match_mode = 'any'} = authorize
        permit = hasRole(roles, role_match_mode)
    }
    return permit
}

/**
 * `v-authorize`：只接受对象参数类型，参数格式如下：
 * <pre>
 *  {
 *      need_permit: true,
 *      permit_by: "auth",
 *      auths: ['authCode1'],
 *      auth_match_mode: 'any',
 *      roles: ['roleCode1'],
 *      role_match_mode: 'any'
 *  }
 * </pre>
 *
 * 其他指令的参数类型为: 字符串数组 OR 字符串
 */
export default function (Vue) {
    const directives = {
        'authorize': {
            inserted: function (el, binding) {
                const {value} = binding
                if (!isObject(value)) {
                    return
                }

                const permit = permitAuthorize(value);

                if (permit === false) { // 无权限，则移除DOM
                    el.parentNode.removeChild(el)
                }
            },

            // operation-bar中按钮的权限控制必须依赖update钩子，因为其依据的meta数据异步加载。
            // FIXME 不过难以避免的还是出现了闪烁。也许这些按钮得通过 render的方式创建VNode
            update: function (el, binding) {
                const {value, oldValue} = binding
                if (!isObject(value)) {
                    return
                }

                if (JSON.stringify(value) == JSON.stringify(oldValue)) {
                    return
                }

                const permit = permitAuthorize(value)

                if (permit === false) { // 无权限，则移除DOM
                    el.parentNode.removeChild(el)
                }
            }
        },
        'any-roles': {
            inserted: function (el, binding) {
                let {value = []} = binding
                if (isString(value)) {
                    value = [value]
                }

                if (!hasAnyRole(value)) {
                    if (el.parentNode) {
                        el.parentNode.removeChild(el)
                    }
                }
            }
        },
        'any-auths': {
            inserted: function (el, binding) {
                let {value = []} = binding
                if (isString(value)) {
                    value = [value]
                }
                if (!hasAnyAuth(value)) {
                    if (el.parentNode) {
                        el.parentNode.removeChild(el)
                    }
                }
            }
        },
        'all-roles': {
            inserted: function (el, binding) {
                let {value = []} = binding
                if (isString(value)) {
                    value = [value]
                }
                if (!hasAllRole(value)) {
                    if (el.parentNode) {
                        el.parentNode.removeChild(el)
                    }
                }
            }
        },
        'all-auths': {
            inserted: function (el, binding) {
                let {value = []} = binding
                if (isString(value)) {
                    value = [value]
                }
                if (!hasAllAuth(value)) {
                    if (el.parentNode) {
                        el.parentNode.removeChild(el)
                    }
                }
            }
        }
    }

    Object.keys(directives).map(key => Vue.directive(key, directives[key]))
}
