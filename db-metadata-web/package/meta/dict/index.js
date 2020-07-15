import DictList from './DictList'

DictList.install = function (Vue) {
    Vue.component(DictList.name, DictList);
}

export default DictList;