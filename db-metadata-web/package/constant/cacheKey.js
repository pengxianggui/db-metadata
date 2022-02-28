const clear = function () {
    Object.values(keyInSession).forEach(key => {
        sessionStorage.removeItem(key)
    })

    Object.values(keyInLocal).forEach(key => {
        localStorage.removeItem(key)
    })
}

const keyInSession = {
}
const keyInLocal = {
    TAG_AFFIX_KEY: 'META-ELEMENT:TAG:AFFIX',
    MENU_COLLAPSE_KEY: 'META-ELEMENT:MENU:COLLAPSE'
}

export default {
    keyInLocal,
    keyInSession,
    clear
}
