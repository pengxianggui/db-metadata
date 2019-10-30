import * as common from './common'
/**
 * @description 对两个对象中的属性和值执行merge操作, 将opt2中的key-value根据key merge到opt1上： 若op1也存在这个key，则取opt2这个key的值
 * 覆盖到opt1上； 若opt1中不存在, 则会被直接追加到opt1中， 因此函数会更改opt1, 执行完后, opt1将是merge后的对象。最后将opt1返回
 * @param opt1
 * @param opt2
 * @param deep
 */
export function merge(opt1, opt2, deep) {
    if (typeof opt1 !== 'object' || typeof opt2 !== 'object') {
        return {}
    }
    if (opt1 === null) {
        return opt2 === null ? {} : opt2;
    }
    deep = (deep === undefined); // 默认deep模式

    let deepMerge = function (obj1, obj2) {
        for (let key in obj2) {
            if (key in obj1) {
                if (typeof obj1[key] === 'object' && typeof obj2[key] === 'object' && deep) {
                    deepMerge(obj1[key], obj2[key])
                } else {
                    obj1[key] = common.deepCopy(obj2[key])
                }
            } else {
                obj1[key] = common.deepCopy(obj2[key])
            }
        }
    };

    deepMerge(opt1, opt2);
    return opt1;
}
