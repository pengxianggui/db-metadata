export default function (value) {
    try {
        if (typeof value == 'string') {
            return JSON.parse(value);
        } else { // object
            return JSON.parse(JSON.stringify(value));
        }
    } catch (e) {
        console.error('value is not legal, a empty object {} will be replaced, value: %o', value);
        return {};
    }
}