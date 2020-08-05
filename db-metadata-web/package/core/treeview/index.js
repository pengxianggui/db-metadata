import TreeView from './src/TreeView'

TreeView.install = function (Vue) {
    Vue.component(TreeView.name, TreeView);
};

export default TreeView;