import {isArray, isEmpty} from "../../../utils/common";

export default function (value) {
    if (isArray(value)) {
        return JSON.stringify(value.filter(i => !isEmpty(i)));
    } else {
        console.error(`[MetaElement] RegionBox参数输入错误, 只接受数组，或可转换为数组的字符串， 请查看RegionBox的入参规则`)
        this.$emit('input', [])
        return "";
    }
}
