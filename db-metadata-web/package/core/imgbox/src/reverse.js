import {isEmpty} from "../../../utils/common";

export default function (value) {
    if (Array.isArray(value)) {
        return value.filter(i => !isEmpty(i)) // 过滤无效内容
    }

    console.error(`[MetaElement] ImgBox输出格式有误， 必须是数组`)
    return []
}
