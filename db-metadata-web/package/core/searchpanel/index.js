import SearchPanel from './src/SearchPanel'

SearchPanel.install = function (Vue) {
    Vue.component(SearchPanel.name, SearchPanel);
};

export default SearchPanel;