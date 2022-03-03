import * as common from './common'


/**
 * @description merge 策略1: 将opt2 merge到opt1, 对于opt1已有的key-value, 保持不变, 对于opt2中新的key-value, 追加到opt1中。传入
 * deep值表示是否深度执行merge逻辑(不传入则为true). 函数将更改opt1的值, 同时返回一个merge后的新对象(opt1的深拷贝)。
 * @param opt1 opt1中的k-v将保留
 * @param opt2 不会改变opt2
 * @param deep 是否深拷贝模式, 默认true
 * @returns {} 返回merge后的opt1的深拷贝对象
 */
export function merge(opt1, opt2, deep = true) {
    let self = this;

    if (opt2 === null || !common.isObject(opt2)) {
        console.warn("typeof opt2: %s , must be 'object' and should not be a null value.", opt2);
        return common.deepClone(opt1);
    }

    if (opt1 === null || !common.isObject(opt1)) {
        console.warn("typeof opt1: %s , must be 'object' and should not be a null value.", opt1);
        return common.deepClone(opt2);
    }

    let deepMerge = function (obj1, obj2) {
        if (!common.isObject(obj1) || !common.isObject(obj2)) return;
        for (let key in obj2) {
            if (!(key in obj1)) {
                set(merge.prototype.Vue, self, obj1, key, common.deepClone(obj2[key]));
            } else {
                if (!deep) return;
                deepMerge(obj1[key], obj2[key])
            }
        }
    };

    // deep merge
    deepMerge(opt1, opt2);
    return common.deepClone(opt1);
}

/**
 * @description merge 策略2： 对两个对象中的属性和值执行merge操作, 将opt2中的key-value根据key merge到opt1上： 若op1也存在这个key，则取opt2这个key的值
 * 覆盖到opt1上； 若opt1中不存在, 则会被直接追加到opt1中， 因此函数会更改opt1, 执行完后, opt1将是merge后的对象。最后将opt1的深拷贝返回
 * @param opt1 opt1中的k-v将被覆盖
 * @param opt2
 * @param deep 是否深拷贝模式, 默认true
 * @returns {} 返回merge后的opt1的深拷贝对象
 */
export function reverseMerge(opt1, opt2, deep = true) {
    let self = this;

    if (opt2 === null || !common.isObject(opt2)) {
        console.warn("typeof opt2: %s , must be 'object' and should not be a null value.", opt2);
        return common.deepClone(opt1);
    }

    if (opt1 === null || !common.isObject(opt1)) {
        console.warn("typeof opt1: %s , must be 'object' and should not be a null value.", opt1);
        return common.deepClone(opt2);
    }

    let deepMerge = function (obj1, obj2) {
        for (let key in obj2) {
            if (key in obj1) {
                if (common.isObject(obj1[key]) && common.isObject(obj2[key]) && deep) {
                    deepMerge(obj1[key], obj2[key])
                } else {
                    set(reverseMerge.prototype.Vue, self, obj1, key, common.deepClone(obj2[key]));
                }
            } else {
                set(reverseMerge.prototype.Vue, self, obj1, key, common.deepClone(obj2[key]));
            }
        }
    };

    deepMerge(opt1, opt2);
    return common.deepClone(opt1);
}

/**
 * keng
 * @param self
 * @param obj
 * @param key
 * @param value
 */
function set(Vue, self, obj, key, value) {
    let vueEnv = false
    try {
        vueEnv = !common.isEmpty(Vue) && self instanceof Vue;
    } catch (err) {
    }

    if (vueEnv) {
        self.$set(obj, key, value);
    } else {
        obj[key] = value;
    }
}

/**
 * 合并两个数组, 两个数组成员都必须是简单类型. 将targetArr合并到sourceArr, 如何sourceArr中已有, 则跳过。如:
 * sourceArr: [1,2,3]
 * targetArr: [3,4]
 * 则合并后, sourceArr变为[1,2,3,4]
 * @param sourceArr
 * @param targetArr
 */
export function mergeArray(sourceArr, targetArr) {
    if (!common.isArray(sourceArr) || !common.isArray(targetArr)) {
        return sourceArr;
    }
    targetArr.filter(ele => sourceArr.indexOf(ele) === -1).forEach(ele => sourceArr.push(ele));
    return sourceArr;
}

/**
 * 将一个或多个对象依次向一个原对象上合并， 并返回元对象，此方法会改变sourceObject
 * @param sourceObject
 * @param targets
 * @returns {*}
 */
export function mergeObject(sourceObject, ...targets) {
    if (!common.isObject(sourceObject)) {
        return sourceObject;
    }

    targets.forEach(target => {
        if (common.isObject(target)) {
            Object.assign(sourceObject, target);
        }
    });
    return sourceObject;
}
