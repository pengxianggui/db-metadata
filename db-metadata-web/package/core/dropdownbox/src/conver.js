import utils from '../../../utils'

export default function (value) {
    let {multiple} = this;
    let tempValue;
    if (multiple) {
        let nativeVal = []
        switch (utils.typeOf(value)) {
            case "[object String]":
                nativeVal = (value.trim() === '' ? [] : value.split(','));
                break
            case "[object Number]":
            case "[object Boolean]":
                tempValue = utils.convertToString(value);
                nativeVal.push(tempValue);
                break
            case "[object Array]":
                nativeVal = value.map(ele => utils.convertToString(ele));
                break
            default:
        }
        return nativeVal
    } else {
        return utils.convertToString(value);
    }
}
