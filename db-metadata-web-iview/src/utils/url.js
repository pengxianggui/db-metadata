import qs from 'qs'
/**
 * compile url with params, only effect for first level variable of params eg:
 * url : /table/list?object_code={objectCode}
 * params: {objectCode: 'xxx'}
 * return /table/list?object_code=xxx
 * @param url
 * @param params
 */
export function compile(url, params) {
    let rex = new RegExp("\{.*?\}", "g");
    let v;
    while (v = url.match(rex)) {
        let xv = v[0].replace(/\{/g, "").replace(/\}/g, "");
        url = url.replace(/\{.*?\}/, params[xv]);
    }
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
