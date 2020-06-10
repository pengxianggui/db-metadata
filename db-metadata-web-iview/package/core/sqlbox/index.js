import SqlBox from './src/SqlBox'

SqlBox.install = function (Vue) {
    Vue.component(SqlBox.name, SqlBox);
};

export default SqlBox;