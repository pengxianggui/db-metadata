import {isEmpty, isString, convertToArray} from "../../../utils/common";

export default function (value) {
    if (isString(value)) {
        return convertToArray(value)
    }
    if (Array.isArray(value)) {
        return value.filter(i => !isEmpty(i)) // 过滤无效内容
    }

    console.error(`[MetaElement] RegionBox输出格式有误， 必须是数组`)
    return []
}
