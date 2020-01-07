import utils from '@/utils'

export default function (value) {   // value is Array
    let newVal = value;
    const {value: inputVal} = this;
    switch (utils.typeOf(inputVal)) {
        case "[object String]": // input: "a,b,c";  innerValue: ['a','b', 'c']; output: "a,b,c";
            newVal = value.join(',');
            break;
    }
    return newVal;
}