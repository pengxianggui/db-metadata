import utils from '../../../utils'

export default function (value) {
    let {multiple} = this;
    if (multiple) {
        let nativeVal = []
        switch (utils.typeOf(value)) {
            case "[object String]":
                nativeVal = (value.trim() === '' ? [] : value.split(','));
                break
            case "[object Number]":
            case "[object Boolean]":
                nativeVal.push(value);
                break
            case "[object Array]":
                nativeVal = value;
                break
            default:
        }
        return nativeVal
    } else {
        return value
    }
}
