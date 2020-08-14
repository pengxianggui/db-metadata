import {isEmpty, isString, isArray, strToArray} from "../../../utils/common";

/**
 * 入参转义, 空值将被转义为空数组
 * @param value {url: string}[] | string , String将被当作url处理
 * @returns {seat: string, name: string, url: string}[]
 */
export default function (value) {
    if (isString(value)) {
        value = strToArray(value)
    }

    if (isArray(value)) {
        return value.filter(i => !isEmpty(i));
    } else {
        console.error(`[MetaElement] 参数 输入错误, 只接受数组，或可转换为数组的字符串， 请查看ImgBox的入参规则:`)
        this.$emit('input', [])
        return [];
    }
}
