import filters from './filter'

let RegisterGlobalFilters = {};
RegisterGlobalFilters.install = function (Vue, opts = {}) {
    for (let key in filters) {
        Vue.filter(key, filters[key]);
    }
};

export default RegisterGlobalFilters;
