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