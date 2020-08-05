import ListView from './src/ListView'

ListView.install = function (Vue) {
    Vue.component(ListView.name, ListView);
};

export default ListView;