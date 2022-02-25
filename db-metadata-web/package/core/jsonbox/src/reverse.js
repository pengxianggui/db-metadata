import {isString, isArray, isObject} from "../../../utils/common";

export default function (value) {
    if (isArray(value) || isObject(value)) {
        return value
    }

    try {
        if (isString(value)) {
            return JSON.parse(value)
        }
    } catch (err) {
        console.error(`[MetaElement] JsonBox输出格式有误，无法转换，输出空对象`)
    }
    return {}
}
