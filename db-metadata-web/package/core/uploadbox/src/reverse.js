import utils from "../../../utils";

/**
 * 出参如果是相对路径，则移除前缀baseURL, 防止前缀被作为路径的一部分保存。
 * @param value 对象数组
 * @returns {*|{}|{}} seat模式下返回单个对象，默认模式下返回数组
 */
export default function (value) {
    const {baseURL, mode} = this // this为UploadItem组件实例
    let arr = [];
    if (utils.isArray(value)) {
        arr = value
    }

    utils.assert(arr.every(f => utils.isObject(f) && !utils.isEmpty(f.url)), "数据错误, 格式必须满足[{url:''}] 或 {url:''}")
    arr = arr.map(f => {
        if (!utils.isExternal(f.url)) {
            f.url = utils.unResolve(baseURL, f.url)
        }
        return f
    })

    const isSeat = (mode !== 'default')
    if (isSeat) {  // seat模式下返回对象
        return arr.length > 0 ? arr[0] : {}
    }
    return arr
}
