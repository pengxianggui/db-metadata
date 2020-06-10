import DataList from './src/DataList'

DataList.install = function (Vue) {
    Vue.component(DataList.name, DataList);
};

export default DataList;