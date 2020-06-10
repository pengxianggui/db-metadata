import utils from '../../../utils'

export default function (value) {
    switch (utils.typeOf(value)) {
        case "[object String]":
            return utils.convertToArray(value);
        case "[object Array]":
            return value
    }
    return value;
}
