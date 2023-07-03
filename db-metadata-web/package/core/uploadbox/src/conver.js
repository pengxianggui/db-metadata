import utils from "../../../utils";
import {deepClone} from "../../../utils/common";

/**
 * 入参如果是相对路径，则拼接前缀baseURL，以便代理命中
 * @param value 对象数组或对象。
 * @returns {*[]} 转换为数组返回, 此数组会传入UploadItem中，因此后者里的nativeValue必定是数组
 */
export default function (value) {
    const {baseURL} = this
    let arr;
    if (utils.isArray(value)) {
        arr = value
    } else {
        arr = utils.isObject(value) ? [value] :[]
    }
    utils.assert(arr.every(f => utils.isObject(f) && !utils.isEmpty(f.url)), "数据错误, 格式必须满足[{url:''}] 或 {url:''}")

    // 此处必须进行深拷贝：conver.js将传入的值中非外部链接的url添加/api前缀，而reverse.js会移除/api前缀。如果不深拷贝，会导致外部的value也会带上/api前缀
    return deepClone(arr).map(f => {
        if (!utils.isExternal(f.url)) {
            f.url = utils.resolve(baseURL, f.url)
        }
        return f
    })
}
