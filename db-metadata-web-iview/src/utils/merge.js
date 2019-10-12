export function merge(opt1, opt2) {
    if (typeof opt1 !== 'object' || typeof opt2 !== 'object') {
        return
    }
    // todo 递归实现 deep merge
    for (let key in opt2) {
        if (!(key in opt1)) {
            opt1[key] = opt2[key]
        }
    }
}
