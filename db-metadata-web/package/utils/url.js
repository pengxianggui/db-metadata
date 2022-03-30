/**
 * compile url with params, only effect for first level variable of params eg:
 * url : /table/list?object_code={objectCode}
 * params: {objectCode: 'xxx'}
 * return /table/list?object_code=xxx
 * @param url
 * @param params
 */
import {assert, isString, isObject, isEmpty, assertUndefined, isArray, isExternal} from "./common";

/**
 * url编译。例如:
 * <pre>
 * url: /table/list?objectCode={objectCode},
 * params: {
 *     objectCode: 'Fuck'
 * }
 * 则编译后返回的结果为: /table/list?objectCode=Fuck
 * 支持多个变量。
 *
 * 若params中不存在某个占位变量, 则此占位变量不会改变。例如, 上面的例子params若是空对象, 那么url会原样输出。
 * 若params中有多余的变量, 在url中没有对应的占位变量, 则会忽略。
 *
 * </pre>
 *
 * @param url
 * @param params
 * @returns {*}
 */
export function compile(url, params = {}) {
    let rex = new RegExp("\{.*?\}", "g");
    let v = url.match(rex);

    if (!isArray(v)) {
        return url
    }

    for (let i = 0; i < v.length; i++) {
        let xv = v[i].replace(/\{/g, "").replace(/\}/g, "");
        if (Object.keys(params).indexOf(xv) > -1) {
            url = url.replace(v[i], assertUndefined(params[xv], ''));
        }
    }

    return url;
}

/**
 * 验证url是否已经编译完毕。若编译完毕则返回true
 * @param url
 */
export function validUrlCompiled(url) {
    let rex = new RegExp("\{.*?\}", "g");
    let v = url.match(rex)
    return isEmpty(v)
}

/**
 * 验证参数是否已经全部编译完毕， 若编译完毕则返回true。比如参数params:
 * <pre>
 * {
 *     "key1": "{value1}"
 *     "key2": "value2"
 * }
 * 其中key1的值就是未编译的, key2的值是编译了的
 * </pre>
 *
 * @param params
 */
export function validParamsCompiled(params) {
    if (isEmpty(params)) { // 空则无需判断是否编译, 返回true
        return true
    }

    for (let key in params) {
        let value = params[key]
        let valueCompiled
        if (isObject(value)) {
            valueCompiled = validParamsCompiled(value)
        } else if (isString(value)) {
            valueCompiled = validUrlCompiled(value)
        } else {
            valueCompiled = true
        }

        if (!valueCompiled) {
            return false
        }
    }

    return true
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

/**
 * 拼接两个path, 判断path参数前缀是否是'/'，防止重复斜杠。
 * 若baseURL或path为空，或path为绝对地址(含有https、http开头)，则直接返回path， 不做拼接
 * @param baseURL
 * @param path
 * @returns {string|*}
 */
export function resolve(baseURL, path) {
    if (isEmpty(baseURL) || isEmpty(path) || isExternal(path)) {
        return path
    }

    if (path.startsWith('/')) {
        return baseURL + path
    } else {
        return baseURL + '/' + path
    }
}
