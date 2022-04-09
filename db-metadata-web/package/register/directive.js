import {hasAnyRole, hasAnyAuth, hasAllRole, hasAllAuth, hasAuth, hasRole} from "../access"
import {isObject, isString} from "../utils/common";

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
            update: function (el, binding) {
                const {value, oldValue} = binding
                if (!isObject(value)) {
                    return
                }

                if (JSON.stringify(value) == JSON.stringify(oldValue)) {
                    return
                }

                const {need_permit = false, permit_by = 'auth'} = binding.value
                if (need_permit !== true) {
                    return
                }

                let permit;
                if (permit_by === 'auth') {
                    const {auths = [], auth_match_mode = 'any'} = binding.value
                    permit = hasAuth(auths, auth_match_mode)
                } else if (permit_by === 'role') {
                    const {roles = [], role_match_mode = 'any'} = binding.value
                    permit = hasRole(roles, role_match_mode)
                }

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
