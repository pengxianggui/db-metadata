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
 * 驼峰转=>?(默认下划线)
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

/**
 * 判断值是否为对象. 数组、null等都将返回false, 只有严格的{}才会返回true
 * @param obj
 */
export function isObject(obj) {
    let toStr = Object.prototype.toString.call(obj);
    return toStr === '[object Object]'
}

export function isArr(obj) {
    let toStr = Object.prototype.toString.call(obj);
    return toStr === '[object Array]'
}

/**
 * 返回值的类型:
 * [object String]、[object Number]、[object Object]、[object Boolean]、
 * [object Array]、[object Function]、[object Null]、[object Undefined]
 * @param value
 * @returns {string}
 */
export function typeOf(value) {
    return Object.prototype.toString.call(value);
}

/**
 * 将数组转为字符串, 如: [1,2,3] => "1,2,3"; ["a","b","c"] => "a,b,c"
 * 如果val不为数组, 不做任何处理, 直接返回val; 如果val是数组, 并且不满足"每个元素都不是数组, 都不是对象"这一条件, 也不做任何处理, 直接返回.
 * @param arr 数组
 * @param separator 分隔符号, 字符串, 默认为 ","
 */
export function joinArr(val, separator) {
    separator = (separator === undefined) ? ',' : separator;
    if (isArr(val) && val.every(item => !isArr(item) && !isObject(item))) {
        return val.join(separator)
    }
    return val;
}

/**
 * 将对象中所有值为数组(数组必须为基本类型数组,即不含有对象和数组元素的数组)的值转为分隔符分隔的字符串.
 * 此方法会改变val值
 * @param val   对象值
 * @param separator 分隔符, 默认为 ","
 * @param deep 是否需要对val进行深度遍历, 默认为false, 即只处理一层
 * @returns {*} 返回处理后的val
 */
export function joinArrInObj(val, separator, deep) {
    separator = (separator === undefined) ? ',' : separator;
    deep = (deep === true) ? true : false;
    if (isObject(val)) {
        for (let key in val) {
            if (deep && isObject(val[key])) {
                val[key] = joinArrInObj(val[key], separator, deep)
            } else {
                val[key] = joinArr(val[key], separator);
            }
        }
    }
    return val;
}