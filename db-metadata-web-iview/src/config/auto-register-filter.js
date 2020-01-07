import filters from '../filters'

let RegisterGlobalFilters = {};
RegisterGlobalFilters.install = function (Vue, opts = {}) {
    for (let key in filters) {
        Vue.filter(key, filters[key]);
    }
};

export default RegisterGlobalFilters;