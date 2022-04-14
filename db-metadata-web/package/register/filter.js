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
        }
    }

    Object.keys(filters).map(key => Vue.filter(key, filters[key]))
}
