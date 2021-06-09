import utils from '../../../utils'

export default function (value) {
    let {multiple} = this;
    let tempValue;
    if (multiple) {
        let nativeVal = []
        console.log(utils.typeOf(value))
        switch (utils.typeOf(value)) {
            case "[object String]":
                nativeVal = (value.trim() === '' ? [] : value.split(','));
            case "[object Number]":
            case "[object Boolean]":
                tempValue = utils.convertToString(value);
                nativeVal.push(tempValue);
            case "[object Array]":
                nativeVal = value.map(ele => utils.convertToString(ele));
            default:
        }
        return nativeVal
    } else {
        return utils.convertToString(value);
    }
}
