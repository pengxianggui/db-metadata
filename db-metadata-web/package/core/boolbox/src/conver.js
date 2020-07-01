import expand from './expand'

export default function (value) {
    return (expand.indexOf(value) + 1) % 2 == 0;
}