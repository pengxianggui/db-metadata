import utils from '../../../utils'

/**
 * 入参转义, 空值将被转义为空数组
 * @param value {url: string}[] | string , String将被当作url处理
 * @returns {seat: string, name: string, url: string}[]
 */
export default function (value) {
    if (utils.isEmpty(value)) {
        return []
    }

    if (utils.isArray(value)) {
        return value.filter(i => !utils.isEmpty(i));
    }

    console.error(`[MetaElement] 参数 输入错误， 请查看ImgBox的入参规则:`)
    return [];
}
