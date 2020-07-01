import expand from './expand'

export default function (value) {
    const {value:inputVal} = this;
    let value_index = expand.indexOf(inputVal);
    let even = (value_index + 1) % 2 == 0;
    let newVal = expand[value_index + (value - even)];
    return newVal;
}