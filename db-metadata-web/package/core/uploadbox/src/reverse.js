import utils from "../../../utils";

/**
 * 出参如果是相对路径，则移除前缀baseURL, 防止前缀被作为路径的一部分保存。
 * @param value 对象数组或对象
 * @returns {*|{}|{}} seat模式下返回单个对象，默认模式下返回数组
 */
export default function (value) {
    let arr;
    if (utils.isArray(value)) {
        arr = value
    } else {
        arr = utils.isObject(value) ? [value] :[]
    }
    utils.assert(arr.every(f => utils.isObject(f) && !utils.isEmpty(f.url)), "数据错误, 格式必须满足[{url:''}] 或 {url:''}")
    arr = arr.map(f => {
        if (!utils.isExternal(f.url)) {
            f.url = utils.unResolve(this.baseURL, f.url)
        }
        return f // 解构可以避免浅拷贝带来的问题
    })

    const isSeat = (this.mode !== 'default')
    if (isSeat) {  // seat模式下返回对象
        return arr.length > 0 ? arr[0] : {}
    }
    return arr
}
