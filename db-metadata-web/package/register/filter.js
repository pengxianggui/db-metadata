import utils from '../utils'

export default function (Vue) {
    const filters = {
        stringify: function (value) {
            return utils.convertToString(value);
        },
        defaultStr: function (value, defaultVal) {
            if (utils.isEmpty(value)) {
                return defaultVal
            }
            return value
        },
        subStr: function (value, length) {
            if (!utils.isString(value) || !utils.isNumber(length)) {
                return value
            }

            if (value.length > length) {
                return value.substring(0, length) + '...'
            }
            return value
        }
    }

    Object.keys(filters).map(key => Vue.filter(key, filters[key]))
}
