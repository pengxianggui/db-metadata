import Tags from './src/Tags'

Tags.install = function (Vue, opt = {}) {
    Vue.component(Tags.name, Tags);
};

export default Tags;