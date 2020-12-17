import {isArray, isEmpty, isString, strToArray} from "../../../utils/common";

export default function (value) {
    if (isString(value)) {
        value = strToArray(value)
    }

    if (isArray(value)) {
        return JSON.stringify(value.filter(i => !isEmpty(i)));
    }

    console.error(`[MetaElement] RegionBox参数输入错误, 只接受数组，或可转换为数组的字符串， 请查看RegionBox的入参规则: ${value}`)
    this.$emit('input', [])
    return "";
}
