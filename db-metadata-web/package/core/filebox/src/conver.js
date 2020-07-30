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

    if (utils.isString(value)) {
        return [{
            url: value,
            name: '',
            seat: ''
        }]
    }

    if (utils.isArray(value) && value.every(i => i.hasOwnProperty('url'))) {
        return value;
    }

    console.error(`[MetaElement] 参数 ${value} 输入错误， 请查看FileBox的入参规则`)
    return [];
}
