/**
 * 设置cookie
 * @param key 键
 * @param value 值
 * @param expire 过期时间，单位: 天
 */
export function setCookie(key, value, expire) {
    let now = new Date();
    now.setTime(now.getTime() + (expire * 24 * 60 * 60 * 1000));
    let expires = "expires=" + now.toGMTString();
    document.cookie = key + "=" + value + "; " + expires;
}

/**
 * 获取cookie
 * @param key
 * @returns {string}
 */
export function getCookie(key) {
    let name = key + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(name) == 0) return c.substring(name.length, c.length);
    }
    return "";
}

/**
 * 删除cookie
 * @param key
 */
export function deleteCookie(key) {
    let past = new Date()
    past.setTime(0) // 将past置为1970年
    let expires = "expires=" + past.toGMTString();
    document.cookie = key + "=" + "; " + expires; // 值置空，过期时间改为过去时间
}
