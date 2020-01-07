import MiniFormField from './src/MiniFormField'
import MiniFormObject from './src/MiniFormObject'

MiniFormField.install = function (Vue) {
    Vue.component(MiniFormField.name, MiniFormField);
    Vue.component(MiniFormObject.name, MiniFormObject);
};

export default {MiniFormField, MiniFormObject};