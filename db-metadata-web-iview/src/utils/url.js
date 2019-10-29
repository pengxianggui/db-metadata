import qs from 'qs'
/**
 * compile url with params, eg:
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

/**
 * 建议的字符串替换函数
 * important!!
 * value中用{{}}标志的占位变量必须是instance中的一级变量
 *
 * 例如: /db/tables?schemaName={{schemaName}}
 *  schemaName -> instance["schemaName"]  要能够成功获取,不支持xxx.abc
 *
 * @param instance
 * @param value  /db/tables?schemaName={{schemaName}}
 * @returns {*}
 */
export function complieVarString(instance, value) {
    let rex = new RegExp("\{\{.*?\}\}", "g");
    let v;
    while (v = value.match(rex)) {
        let xv = v[0].replace(/\{\{/g, "").replace(/\}\}/g, "");
        value = value.replace(/\{\{.*?\}\}/, instance[xv]);
    }
    return value;
}
