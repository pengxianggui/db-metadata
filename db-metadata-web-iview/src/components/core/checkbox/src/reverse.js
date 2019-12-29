import utils from '@/utils'

export default function (value) {
    let newVal = value;
    const {value: inputVal} = this;
    switch (utils.typeOf(inputVal)) {
        case "[object String]":
            newVal = value.join(',');
            break;
    }
    return newVal;
}