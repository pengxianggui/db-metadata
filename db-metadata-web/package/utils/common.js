import cloneDeep from 'lodash/cloneDeep'
import toString from 'lodash/toString'
import toNumber from 'lodash/toNumber'

// 常规utils方法

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

export function isNull(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object Null]'
}

export function isUndefined(val) {
    let toStr = Object.prototype.toString.call(val);
    return toStr === '[object Undefined]'
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
 * 判断一个值是否为空.
 * 如果是
 * 1. 字符串, 则判断是否为空字符串(空格也被视为空)
 * 2. 对象, 则判断是否无任何键值
 * 3. 数组, 则判断是否无任何数组成员
 * 4. null, 返回true
 * 5. undefined, true
 * 6. 其他情况均返回false
 */
export function isEmpty(value) {
    switch (typeOf(value)) {
        case '[object String]':
            return value.trim() === '';
        case "[object Object]":
            return Object.keys(value).length === 0;
        case "[object Array]":
            return value.length === 0;
        case "[object Undefined]":
        case "[object Null]":
            return true;
    }
    return false;
}

/**
 * 将字符串转为对象或数组
 * @param str
 * @returns {{}|any}
 */
export function parse(str) {
    if (isEmpty(str)) {
        return {}
    }

    return JSON.parse(str)
}

/**
 * 提供一个keys数组和一个对象, 从此对象中提取出keys数组对应的values数组, eg:
 * object: {
 *     a: '1',
 *     b: '2',
 *     c: '3'
 * },
 * keys: ['a', 'c']
 * 则返回: [1,3];
 * 若keys为"b", 则直接返回[2]
 *
 * @param object Object
 * @param keys Array
 * @return Array, 若没有找到相应的value, 则会返回空数组
 */
export function extractValue(object, keys) {
    if (!isObject(object)) return [];

    let values = [];
    switch (typeOf(keys)) {
        case "[object String]":
            values.push(object[keys]);
            break;
        case "[object Array]":
            keys.forEach(key => values.push(object[key]));
            break;
    }
    return values;
}

/**
 * 拼接kvs, 如：
 * keys: ['a', 'b', 'c']
 * values: ['1', 2, '3']
 * 结果为: "a_1,b_2,c_3"
 * @param keys
 * @param values
 * @param separatorBetweenKAndV 默认为"_"
 * @param separatorBetweenKVs 默认为","
 * @returns {string}
 */
export function spliceKvs(keys, values, separatorBetweenKAndV = '_', separatorBetweenKVs = ',') {
    if (!isArray(keys) || !isArray(values)) {
        throw "keys:" + keys + "; values:" + values + ", all should be Array!";
    }
    if (keys.length !== values.length) {
        throw "keys.length not equal values.length, so can't splice.";
    }

    let kvs = [];
    for (let i = 0; i < keys.length; i++) {
        let key = toString(keys[i]);
        let value = toString(values[i]);
        kvs.push(key + separatorBetweenKAndV + value);
    }
    return kvs.join(separatorBetweenKVs);
}

/**
 * 拼接kv, 如:
 * key: a
 * value: 1
 * 结果为: a_b
 * @param key
 * @param value
 * @param separatorBetweenKAndV
 * @returns {string}
 */
export function spliceKv(key, value, separatorBetweenKAndV = '_') {
    if (!isString(key)) {
        throw "key:" + key + " should be String!";
    }
    key = toString(key)
    value = toString(value)
    return key + separatorBetweenKAndV + value;
}

/**
 * 将字符串转换为Array, 并返回。若无法转换或转换失败，则返回空数组
 * @param value
 */
export function convertToArray(value) {
    if (isArray(value)) return value;

    if (!isString(value)) return [];
    let result;
    try {
        result = JSON.parse(value);
        return isArray(result) ? result : [];
    } catch (e) {
        return [];
    }
}

/**
 * 将字符串值转为对象, 若转换失败, 则返回原值
 * 若不为字符串则直接返回原值
 *
 * @param value
 * @returns {{}|*}
 */
export function strToObject(value) {
    if (isString(value)) {
        let result;
        try {
            result = JSON.parse(value);
            return isObject(result) ? result : value;
        } catch (e) {
            console.warn(`value can't be conver to Object, attention please. value:${value}`);
            return value;
        }
    }
    return value
}

/**
 * 将字符串值转为数组, 若转换失败, 则返回原值
 * 若不为字符串则直接返回原值
 *
 * @param value
 * @returns {{}|*}
 */
export function strToArray(value) {
    if (isString(value)) {
        let result;
        try {
            result = JSON.parse(value);
            return isArray(result) ? result : value;
        } catch (e) {
            console.warn(`value can't be conver to Array, attention please. value:${value}`);
            return value;
        }
    }
    return value
}

/**
 * 将字符串转换为数组。value就是数组，则直接返回，若是字符串，则尝试用指定分隔符(默认",")分隔为数组。
 * 若value既不是数组，也不是字符串，则直接返回空数组。
 * @param value
 * @param separator
 * @returns {*}
 */
export function strSplitToArray(value, separator = ',') {
    if (isArray(value)) {
        return value;
    }

    if (isString(value)) {
        return value.split(separator);
    }

    return []
}

/**
 * 如果 value 不是字符串，将其转换为字符串。 null 和 undefined 将返回空字符串。
 * toString(null);
 * // => ''
 *
 * toString(-0);
 * // => '-0'
 *
 * toString([1, 2, 3]);
 * // => '1,2,3'
 *
 * @param value
 * @returns {string}
 */
export function convertToString(value) {
    return toString(value);
}

export function convertToNumber(value) {
    return toNumber(value);
}

/**
 * 将数组转为字符串, 如: [1,2,3] => "1,2,3"; ["a","b","c"] => "a,b,c"
 * 如果val不为数组, 不做任何处理, 直接返回val; 如果val是数组, 并且不满足"每个元素都不是数组, 都不是对象"这一条件, 也不做任何处理, 直接返回.
 * @param arr 数组
 * @param separator 分隔符号, 字符串, 默认为 ","
 */
export function joinArr(val, separator = ',') {
    if (isArray(val) && val.every(item => !isArray(item) && !isObject(item) && !isFunction(item))) {
        return val.join(separator)
    }
    return val;
}

/**
 * 将对象中所有值为数组(数组必须为基本类型数组,即不含有对象和数组元素的数组)的值转为分隔符分隔的字符串.
 * 此方法会改变val值. 如:
 * val: {
 *     'a': 1,
 *     'b': [1,2],
 *     'c': ['A', 'B']
 * }
 * 返回: {
 *     'a': 1,
 *     'b': '1,2'
 *     'c': 'A,B'
 * }
 * @param val   对象值
 * @param separator 分隔符, 默认为 ","
 * @param deep 是否需要对val进行深度遍历, 默认为false, 即只处理一层
 * @returns {*} 返回处理后的val
 */
export function joinArrInObj(val, separator = ',', deep = false) {
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

/**
 * 将对象params中value为空字符串(不含空格)的值改为null
 * @param params
 */
export function filterEmptyStrToNull(params) {
    if (isObject(params)) {
        Object.keys(params).forEach(k => {
            if (isString(params[k]) && isEmpty(params[k])) {
                params[k] = null
            } else if (isObject(params[k])) {
                filterEmptyStrToNull(params[k])
            }
        })
    }
}

export function isImageFile(file) {
    if (file instanceof File) {
        return file.type.startsWith('image/');
    }
    return false;
}

/**
 * 若传入value为undefined, 则返回提供的默认值, 否则返回value
 * @param value
 * @param defaultValue
 * @returns {*}
 */
export function assertUndefined(value, defaultValue) {
    if (isUndefined(value)) return defaultValue;
    return value;
}

/**
 * 若传入的value为empty(null, undefined, [], {}, 空字符串(空格也算)), 则返回defaultValue, 否则返回value
 * @param value
 * @param defaultValue
 * @returns {*}
 */
export function assertEmpty(value, defaultValue) {
    if (isEmpty(value)) return defaultValue;
    return value;
}

/**
 * 若传入的value不为Object类型, 则返回defaultValue, 否则返回value
 * @param value
 * @param defaultValue
 * @returns {*}
 */
export function assertObject(value, defaultValue) {
    if (!isObject(value)) return defaultValue;
    return value;
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
    if (!isArray(arr)) return deepClone(arr);
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
        return deepClone(arr);
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
 * 判断两个对象的属性是否全部都相同. 两个对象必须都为对象, 否则返回true;
 * key为数组时, 必须满足key中每一个元素, object1，object2都有并且对应值都相等，才返回true;
 * key为字符串时, 必须满足object1,object2都含有key, 并且对应值相等，才返回true;
 * @param object1 Object
 * @param object2 Object
 * @param keys Array
 * @return Boolean
 */
export function allEqualOnKeys(object1, object2, keys) {
    if (!isObject(object1) || !isObject(object2)) return false;
    if (!isArray(keys) && !isString(keys)) return false;

    if (isString(keys)) {
        if (object1.hasOwnProperty(keys) && object2.hasOwnProperty(keys)) {
            return object1[keys] === object2[keys];
        }
    } else if (isArray(keys)) {
        return keys.every(ele => object1.hasOwnProperty(ele) && object2.hasOwnProperty(ele) && (object1[ele] === object2[ele]));
    }
    return false;
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
    return cloneDeep(val);  // API: http://lodash.think2011.net/cloneDeep, Or other API function of lodash. It's useful
}

/**
 * 判断对象是否含有某个属性
 * @param object
 * @param key
 */
export function hasProp(object, key) {
    if (!isObject(object)) return false;

    return object.hasOwnProperty(key);
}

/**
 * 断言对象中是否含有某个或某些属性, 传入多个断言属性, 从左到右, 只要存在一个, 立马返回这个属性名
 * @param object
 * @param keys
 * @returns {boolean|*}
 */
export function assertProp(object, ...keys) {
    if (!isObject(object)) return false;

    for (let i = 0; i < keys.length; i++) {
        if (hasProp(object, keys[i])) {
            return keys[i];
        }
    }
    return false;
}

/**
 * 提取函数字符串中函数体. 如, 有以下**字符串**:
 *
 *   function(h, value) {
 *       return h("span", {
 *           attrs: {
 *               style: "color: red",
 *           }
 *       }, value);
 *   }
 *
 *
 *
 *
 * @param fnStr
 * @returns Function
 */
export function strToFn(fnStr) {
    if (isFunction(fnStr)) return fnStr;
    if (!isString(fnStr) || isEmpty(fnStr)) throw "fnStr is not a valid string";

    const firstCurlyBraces = fnStr.indexOf('{');
    const lastCurlyBraces = fnStr.lastIndexOf('}');
    const fnBody = fnStr.substring(firstCurlyBraces + 1, lastCurlyBraces);

    const firstParentheses = fnStr.indexOf('(');
    const lastParentheses = fnStr.indexOf(')');
    const params = fnStr.substring(firstParentheses + 1, lastParentheses);
    return new Function(...params.split(","), fnBody);
}

/**
 * @param {string} url
 * @returns {Object}
 */
export function param2Obj(url) {
    const search = url.split('?')[1]
    if (!search) {
        return {}
    }
    return JSON.parse(
        '{"' +
        decodeURIComponent(search)
            .replace(/"/g, '\\"')
            .replace(/&/g, '","')
            .replace(/=/g, '":"')
            .replace(/\+/g, ' ') +
        '"}'
    )
}


/**
 * 是否外网链接
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
    return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * 断言， 条件成立通过, 成立抛出错误
 * @param condition
 * @param msg
 */
export function assert(condition, msg) {
    if (!condition) {
        // throw new Error(`${msg}`)
        console.error(msg)
    }
}

export function printErr(msg) {
    console.error(`[meta-element]${msg}`)
}

export function printInfo(msg) {
    console.info(`[meta-element]${msg}`)
}

export function printWarn(msg) {
    console.warn(`[meta-element]${msg}`)
}

/**
 * 运行一个函数。返回运行结果。若不是函数，则返回其值
 * @param value
 * @param params 参数数组
 * @returns {*}
 */
export function execute(fn, params) {
    if (isFunction(fn)) {
        let s = fn.apply(null, params)
        return s
    } else {
        alert('sss')
        return fn
    }
}

/**
 * 删除一个对象所有的属性
 * @param value
 */
export function deleteAllAttrs(value) {
    if (!isObject(value)) {
        return {}
    }

    for (let key of Object.keys(value)) {
        delete value[key]
    }
    return value
}

/**
 * 剪切一个对象的属性，并返回其属性值。剪切后改对象无此属性
 * @param obj
 * @param attr
 * @returns {null}
 */
export function shearAttr(obj, attr) {
    if (!isObject(obj)) {
        return null
    }

    let result = obj[attr]
    delete obj[attr]
    return result
}

/**
 * 生成指定长度的随机数
 * @param length
 * @returns {string}
 */
export function randomNum(length) {
    let num = "";
    for (let i = 0; i < length; i++) {
        num += Math.floor(Math.random() * 10);
    }
    return num;
}

/**
 * 分组。例如: 传入:
 *
 * <pre>
 *     arr:
 *     [
 *      {name: '张三', 'job': '开发'},
 *      {name: '李四', 'job': '开发'},
 *      {name: '王五', 'job': '测试'},
 *      {name: '赵六', 'job': ''},
 *      {name: '胜七'}
 *     ]
 *     key: job
 *     defGroupName: '未知'
 *
 *     则返回的值为:
 *     {
 *         "开发": [{name: '张三', 'job': '开发'}, {name: '李四', 'job': '开发'}],
 *         "测试": [{name: '王五', 'job': '测试'}],
 *         "未知": [{name: '赵六', 'job': ''}, {name: '胜七'}]
 *     }
 * </pre>
 *
 * @param arr 对象数组，其中的对象需要含有key属性。若arr不是数组，或key为空，则返回空对象
 * @param key
 * @param defGroupName 若arr中的对象元素不含有key属性, 或其key属性的值为空, 那么建立一个默认组(组名为defGroupName), 并将其放入。此入参不传则为"默认"
 * @return {} 返回分组后的对象。
 */
export function group(arr, key, defGroupName = '默认') {
    if (!isArray(arr) || isEmpty(key)) {
        return {}
    }

    let map = {}
    arr.forEach(obj => {
        let groupK = (isEmpty(obj[key]) ? defGroupName : obj[key])
        if (map.hasOwnProperty(groupK)) {
            map[groupK].push(obj)
        } else {
            map[groupK] = new Array(obj)
        }
    })
    return map
}

/**
 * 在范围内随机生成一个整数。范围为左右闭区间
 * @param begin 整数
 * @param end 整数
 */
export function randomInt(begin, end) {
    return Math.floor(Math.random() * ((end + 1) - begin) + 1)
}
