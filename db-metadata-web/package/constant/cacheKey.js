const clear = function () {
    Object.values(keyInSession).forEach(({autoClear, value}) => {
        if (autoClear) {
            sessionStorage.removeItem(value)
        }
    })

    Object.values(keyInLocal).forEach(({autoClear, value}) => {
        if (autoClear) {
            localStorage.removeItem(value)
        }
    })
}

const keyInSession = {
}
const keyInLocal = {
    TAG_AFFIX_KEY: {
        value: 'META-ELEMENT:TAG:AFFIX',
        autoClear: true
    },
    MENU_COLLAPSE_KEY: {
        value: 'META-ELEMENT:MENU:COLLAPSE',
        autoClear: false
    },
    THEME_DATA_KEY: {
        value: 'META-ELEMENT:THEME:DATA',
        autoClear: false
    },
    THEME_OVERVIEW: {
        value: 'META-ELEMENT:THEME:OVERVIEW',
        autoClear: false
    }
}

export default {
    keyInLocal,
    keyInSession,
    clear
}
