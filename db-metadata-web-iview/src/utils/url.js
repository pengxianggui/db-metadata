import qs from 'qs'
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
    let params = qs.stringify(paramsMap);
    if (url.indexOf('?') !== -1) {
        return url + '&' + params;
    }
    return url + '?' + params;
}