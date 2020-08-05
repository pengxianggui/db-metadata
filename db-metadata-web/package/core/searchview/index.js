import SearchView from './src/SearchView'

SearchView.install = function (Vue) {
    Vue.component(SearchView.name, SearchView);
};

export default SearchView;