import {hasAnyRole, hasAnyAuth, hasAllRole, hasAllAuth, hasRole, hasAuth} from "../access"
import {strSplitToArray} from "../utils/common"

export default {
    'menu-auth': {
        inserted: function (el, binding) {
            const {need_permit = true, permit_by = 'auth', auths = [], roles = [], auth_match_mode = 'any', role_match_mode = 'any'} = binding.value
            if (!need_permit) {
                return
            }

            let show;
            if (permit_by == 'role') {
                // 数据库中roles可能是以英文逗号分隔的字符串。而编程菜单采用的是数组，因此兼容处理下
                show = hasRole(strSplitToArray(roles, ','), role_match_mode)
            } else {
                // 数据库中auths可能是以英文逗号分隔的字符串。而编程菜单采用的是数组，因此兼容处理下
                show = hasAuth(strSplitToArray(auths, ','), auth_match_mode)
            }

            if (show) {
                return
            }

            el.parentNode.removeChild(el)
        }
    },
    'any-roles': {
        inserted: function (el, binding) {
            if (!hasAnyRole(binding.value)) {
                el.parentNode.removeChild(el)
            }
        }
    },
    'any-auths': {
        inserted: function (el, binding) {
            if (!hasAnyAuth(binding.value)) {
                el.parentNode.removeChild(el)
            }
        }
    },
    'all-roles': {
        inserted: function (el, binding) {
            if (!hasAllRole(binding.value)) {
                el.parentNode.removeChild(el)
            }
        }
    },
    'all-auths': {
        inserted: function (el, binding) {
            if (!hasAllAuth(binding.value)) {
                el.parentNode.removeChild(el)
            }
        }
    }
}
