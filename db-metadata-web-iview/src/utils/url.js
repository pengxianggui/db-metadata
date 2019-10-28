/**
 * compile url with params, eg:
 * url : /table/list?object_code=?
 * params: {object_code: 'xxx'}
 * return /table/list?object_code=xxx
 * @param url
 * @param params
 */
export function compile(url, params) {
    // TODO

    return url;
}

/**
 * 拼接url
 * @param url /xx/yy
 * @param paramsMap {'param1': 111, 'param2': 222}
 * @return /xx/yy?param1=111&param2=222
 */
export function splice(url, paramsMap) {
    let paramArr = [];
    for (let key in paramsMap) {
        paramArr.push('key=' + paramsMap[key])
    }
    if (url.indexOf('?') !== -1) {
        return url + '&' + paramArr.join('&')
    }
    return url + '?' + paramArr.join('&')
}