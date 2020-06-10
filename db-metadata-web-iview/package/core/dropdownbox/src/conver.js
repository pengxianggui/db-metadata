import utils from '../../../utils'

export default function (value) {
    let {multiple} = this;
    let tempValue;
    if (multiple) {
        switch (utils.typeOf(value)) {
            case "[object String]":
                return value.trim() === '' ? [] : value.split(',');
            case "[object Number]":
            case "[object Boolean]":
                tempValue = utils.convertToString(value);
                return [tempValue];
            case "[object Array]":
                return value.map(ele => utils.convertToString(ele));
            default:
                return [];
        }
    }
    return utils.convertToString(value);
}
