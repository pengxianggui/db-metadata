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
