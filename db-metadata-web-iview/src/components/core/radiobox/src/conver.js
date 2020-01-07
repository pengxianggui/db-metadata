import utils from '@/utils'

export default function (value) {
    switch (utils.typeOf(value)) {
        case "[object Number]":
        case "[object Boolean]":
            return utils.convertToString(value);
        default:
            return value;
    }
}