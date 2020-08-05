import TableTreeView from './src/TableTreeView'

TableTreeView.install = function (Vue) {
    Vue.component(TableTreeView.name, TableTreeView);
};

export default TableTreeView;