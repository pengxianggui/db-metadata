import {isArray, isString, isObject, strToArray, strToObject} from "../../../utils/common";

export default function (value) {
    if (isString(value)) {
        value = strToObject(value)
        value = strToArray(value)
    }

    if (isArray(value) || isObject(value)) {
        return value;
    } else {
        console.warn(`[MetaElement] 参数:${value}输入错误, 只接受json数组、json对象，或可转换为这两个的字符串,请查看JsonBox的入参规则. 系统将自动转为空对象！`)
        this.$emit('input', {})
        return {};
    }
}