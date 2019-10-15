export function merge(opt1, opt2, deep) {
    // if (typeof opt1 !== 'object' || typeof opt2 !== 'object') {
    //     return
    // }
    deep = (deep === undefined) // 默认deep模式

    let deepMerge = function (obj1, obj2) {
        if (typeof obj1 !== 'object' || typeof obj2 !== "object") return
        for (let key in obj2) {
            if (!(key in obj1)) {
                obj1[key] = obj2[key]
            } else {
                if (!deep) return
                deepMerge(obj1[key], obj2[key])
            }
        }
    }

    // deep merge
    deepMerge(opt1, opt2)
}
