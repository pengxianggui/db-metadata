/**
 * compile url with params, only effect for first level variable of params eg:
 * url : /table/list?object_code={objectCode}
 * params: {objectCode: 'xxx'}
 * return /table/list?object_code=xxx
 * @param url
 * @param params
 */
import {assert, isString, isObject, isEmpty} from "./common";

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
 * 将对象类型参数拼接到url后
 * @param url
 * @param params
 * @returns {*}
 */
export function resolvePath(url, params) {
    assert(isString(url), `[MetaElement] 参数url: ${url}: 必须为字符串`)
    assert(isObject(params), `[MetaElement] 参数params: ${params}: 必须为对象`)

    if (isEmpty(params) || isEmpty(url)) {
        return url
    }

    url += (url.indexOf('?') > -1 ? '&' : '?')

    const paramUrl = JSON.stringify(params).replace(/:/g, '=')
        .replace(/"/g, '')
        .replace(/,/g, '&')
        .match(/\{([^)]*)\}/)

    return url + paramUrl[1]
}