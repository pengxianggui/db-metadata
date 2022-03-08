import {hasAnyRole, hasAnyAuth, hasAllRole, hasAllAuth} from "../access"

export default function (Vue) {
    const directives = {
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
