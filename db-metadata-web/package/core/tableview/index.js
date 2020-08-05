import TableView from './src/TableView'

TableView.install = function (Vue) {
    Vue.component(TableView.name, TableView);
};

export default TableView;