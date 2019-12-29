import utils from '@/utils'

export default function (value) {
    let newVal = value;
    let {value: inputVal, multiple} = this;

    if (multiple) {
        switch (utils.typeOf(inputVal)) {
            case "[object String]":
                newVal = value.join(',');
                break;
            case "[object Array]":
                newVal = value;
                break;
            case "[object Number]":
                newVal = utils.convertToNumber(value);
                break;
        }
    }
    return newVal;
}