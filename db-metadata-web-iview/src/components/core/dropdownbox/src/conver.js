import utils from '@/utils'

export default function (value) {
    let {multiple} = this;
    if (multiple) {
        switch (utils.typeOf(value)) {
            case "[object String]":
                return value.trim() === '' ? [] : value.split(',');
            case "[object Array]":
                return value.map(ele => utils.convertToString(ele));
            case "[object Undefined]":
                return [];
            case "[object Null]":
                return [];
        }
    }
    return utils.convertToString(value);
}