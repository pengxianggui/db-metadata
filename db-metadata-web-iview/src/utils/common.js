// 常规utils方法
/**
 * 深拷贝
 * @param obj
 * @returns {Array|*}
 */
export function deepCopy(obj) {
    if (typeof obj !== "object" || !obj)
        return obj;
    let copy;
    if (Array.isArray(obj)) {
        copy = [];
        for (let key = 0; key < obj.length; key++) {
            copy[key] = deepCopy(obj[key]);
        }
        return copy;
    }
    let cons = obj.constructor;
    if (cons === RegExp)
        return obj;

    copy = new cons();
    for (let key in obj) {
        copy[key] = deepCopy(obj[key]);
    }
    return copy;
}


/**
 * 驼峰转=>下划线(默认)
 * @param str
 * @param separator
 * @returns {*}
 */
export function camelCaseTo(str, separator) {
    separator = (separator === undefined ? "_" : separator);

    let temp = str.replace(/[A-Z]/g, function (match) {
        return separator + match.toLowerCase();
    });
    if (temp.slice(0, separator.length) === separator) { //如果首字母是大写，执行replace时会多一个_，这里需要去掉
        temp = temp.slice(1);
    }
    return temp
}