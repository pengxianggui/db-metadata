import utils from '@/utils'

export default function (value) {
    switch (utils.typeOf(value)) {
        case "[object String]":
            return value.trim() === '' ? [] : value.split(',');
        case "[object Undefined]":
            return [];
        case "[object Null]":
            return [];
    }
    return value;
}