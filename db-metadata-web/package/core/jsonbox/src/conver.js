import {isArray, isString, isObject, convertToArray, convertToObject} from "../../../utils/common";

export default function (value) {
    if (isString(value)) {
        value = convertToObject(value, false)
        value = convertToArray(value, false)
    }

    if (isArray(value) || isObject(value)) {
        return value;
    } else {
        console.warn(`[MetaElement] 参数:${value}输入错误, 只接受json数组、json对象，或可转换为这两个的字符串,请查看JsonBox的入参规则. 系统将自动转为空对象！`)
    }
}
