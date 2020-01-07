import utils from '@/utils'

export default function (value) {
    let newVal = value;
    let {value: inputVal, multiple} = this;

    if (multiple) {
        switch (utils.typeOf(inputVal)) {
            case "[object String]":
                newVal = value.join(',');
                break;
        }
    }
    return newVal;
}