import utils from '../../../utils'

export default function (value) {   // value could be String or Array
    switch (utils.typeOf(value)) {
        case "[object String]":
            return value.trim() === '' ? [] : value.split(','); // "1,2,3" => ["1", "2", "3"]
        case "[object Array]":
            return value.map(ele => utils.convertToString(ele));    // [1,2,3] => ["1", "2", "3"]   (统一转为字符串)
        default:
            return [];
    }
}
