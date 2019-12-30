import ListItem from './src/ListItem'

ListItem.install = function (Vue) {
    Vue.component(ListItem.name, ListItem);
};

export default ListItem;