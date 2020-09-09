import expand from './expand'

export default function (value) {
    if (expand.indexOf(value) < 0) {
        return false
    }
    return (expand.indexOf(value) + 1) % 2 == 0;
}