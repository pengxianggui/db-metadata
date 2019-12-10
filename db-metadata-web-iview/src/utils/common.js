import cloneDeep from 'lodash/cloneDeep'

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
export function isObject(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object Object]'
}

export function isArray(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object Array]'
}

export function isBoolean(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object Boolean]'
}

export function isString(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object String]'
}

export function isNumber(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object Number]'
}

export function isFunction(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object Function]'
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
 * 将字符串转换为Array, 并返回。若无法转换或转换失败，则返回false
 * @param value
 */
export function convertToArray(value) {
    if (isArray(value)) return value;

    if (!isString(value)) return false;
    let result;
    try {
        result = JSON.parse(value);
        return isArray(result);
    } catch (e) {
        return false;
    }
}

/**
 * 将数组转为字符串, 如: [1,2,3] => "1,2,3"; ["a","b","c"] => "a,b,c"
 * 如果val不为数组, 不做任何处理, 直接返回val; 如果val是数组, 并且不满足"每个元素都不是数组, 都不是对象"这一条件, 也不做任何处理, 直接返回.
 * @param arr 数组
 * @param separator 分隔符号, 字符串, 默认为 ","
 */
export function joinArr(val, separator) {
    separator = (separator === undefined) ? ',' : separator;
    if (isArray(val) && val.every(item => !isArray(item) && !isObject(item))) {
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

export function isImageFile(file) {
    if (file instanceof File) {
        return file.type.startsWith('image/');
    }
    return false;
}

/**
 * 提取数组中不重复的元素(重复规则看keyName), 只保留重复中的第一个. 返回提取后的新数组。例如:
 * 指定:
 * arr: [
 *  {key: 'key1', value: '1'},
 *  {key: 'key2', value: '0'}
 *  {key: 'key1', value: '0'}
 * ]
 * keyName: key
 * 则将返回:
 * [
 *  {key: 'key1', value: '1'},
 *  {key: 'key2', value: '0'}
 * ]
 *
 * 同时不含有key键的元素也会被移除.
 *
 * 当元素全部为同一类基本类型时, 移除重复的元素, 返回去重后的数组。 方法不改变原数组
 *
 * @param arr
 * @param keyName
 */
export function extractNotRepeatEle(arr, keyName) {
    if (!isArray(arr)) return deepCopy(arr);
    let eleType;

    if (arr.every(ele => isObject(ele))) {
        eleType = '[object Object]'
    } else if (arr.every(ele => isString(ele))) {
        eleType = '[object String]'
    } else if (arr.every(ele => isNumber(ele))) {
        eleType = '[object Number]'
    } else if (arr.every(ele => isBoolean(ele))) {
        eleType = '[object Boolean]'
    } else {
        return deepCopy(arr);
    }

    let newArr = [];
    let inNewArr = function (val) {
        return newArr.map(item => item[keyName]).indexOf(val) > -1
    };

    arr.forEach(item => {
        switch (eleType) {
            case '[object Object]':
                if (item.hasOwnProperty(keyName) && !inNewArr(item[keyName])) {
                    newArr.push(deepClone(item));
                }
                break;
            default:
                if (!newArr.indexOf(item) > -1) {
                    newArr.push(deepClone(item));
                }
                break;
        }
    });
    return newArr;
}

/**
 * 标记重复元素, 无返回值
 * @param arr: 对象数组
 * @param keyName   指定重复依据的键名
 * @param notRepeatCallback 回调函数, 参数为每个非重复的元素
 * @param repeatCallback 回调函数, 参数为每个重复的元素
 */
export function markNotRepeatEle(arr, keyName, notRepeatCallback, repeatCallback) {
    if (!isArray(arr) || !arr.every(ele => isObject(ele))) return;

    let tempArr = [];
    let inTempArr = function (val) {
        return tempArr.map(item => item[keyName]).indexOf(val) > -1
    };

    arr.forEach(item => {
        if (item.hasOwnProperty(keyName) && !inTempArr(item[keyName])) {
            tempArr.push(deepClone(item));
            if (isFunction(notRepeatCallback)) notRepeatCallback(item);
        } else {
            if (isFunction(repeatCallback)) repeatCallback(item);
        }
    });
}

export function deepClone(val) {
    return cloneDeep(val);
}