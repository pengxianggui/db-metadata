/**
 * 对两个对象中的属性和值, 执行merge操作, 以opt2的key为准覆盖opt1中的value
 * @param opt1
 * @param opt2
 * @param deep
 */
export function merge(opt1, opt2, deep) {
    if (typeof opt1 !== 'object' || typeof opt2 !== 'object') {
        return
    }
    deep = (deep === undefined) // 默认deep模式

    let deepMerge = function (obj1, obj2) {
        // TODO optimized
        for (let key in obj2) {
            if (key in obj1) {
                if (typeof obj1[key] === 'object' && typeof obj2[key] === 'object' && deep) {
                    deepMerge(obj1[key], obj2[key])
                } else {
                    obj1[key] = obj2[key]
                }
            } else {
                obj1[key] = obj2[key]
            }
        }
    }
    // deep merge
    deepMerge(opt1, opt2)
}
