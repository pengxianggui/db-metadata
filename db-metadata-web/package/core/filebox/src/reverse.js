import {isEmpty} from "../../../utils/common";

export default function (value) {
    if (Array.isArray(value)) {
        return value.filter(i => !isEmpty(i))
    }

    console.error(`[MetaElement] FileBox输出格式有误， 必须是数组`)
    return []
}
