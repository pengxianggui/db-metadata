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

    copy = cons();
    for (let key in obj) {
        copy[key] = deepCopy(obj[key]);
    }
    return copy;
};