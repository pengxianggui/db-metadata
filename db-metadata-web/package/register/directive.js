import {hasAnyRole, hasAnyAuth, hasAllRole, hasAllAuth, hasAuth, hasRole} from "../access"
import {isObject} from "../utils/common";

// TODO FIXME 2.2 当指令绑定的值是异步获取时，无法做到响应式. 导致每次这里取到的值(binding.value)都是undefined
export default function (Vue) {
    const directives = {
        'authorize': {
            inserted: function (el, binding) {
                Vue.nextTick(() => {
                    if (!isObject(binding.value)) {
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
                })
            }
        },
        'any-roles': {
            inserted: function (el, binding) {
                Vue.nextTick(() => {
                    if (!hasAnyRole(binding.value)) {
                        if (el.parentNode) {
                            el.parentNode.removeChild(el)
                        }
                    }
                })
            }
        },
        'any-auths': {
            inserted: function (el, binding) {
                if (!hasAnyAuth(binding.value)) {
                    Vue.nextTick(() => {
                        if (el.parentNode) {
                            el.parentNode.removeChild(el)
                        }
                    })
                }
            }
        },
        'all-roles': {
            inserted: function (el, binding) {
                Vue.nextTick(() => {
                    if (!hasAllRole(binding.value)) {
                        if (el.parentNode) {
                            el.parentNode.removeChild(el)
                        }
                    }
                })
            }
        },
        'all-auths': {
            inserted: function (el, binding) {
                Vue.nextTick(() => {
                    if (!hasAllAuth(binding.value)) {
                        if (el.parentNode) {
                            el.parentNode.removeChild(el)
                        }
                    }
                })
            }
        }
    }

    Object.keys(directives).map(key => Vue.directive(key, directives[key]))
}
