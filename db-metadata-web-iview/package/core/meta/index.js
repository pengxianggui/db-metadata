import MiniFormField from './src/MiniFormField'
import MiniFormObject from './src/MiniFormObject'
import MetaEasyEdit from "./src/MetaEasyEdit";

MiniFormField.install = function (Vue) {
    Vue.component(MiniFormField.name, MiniFormField);
};

MiniFormObject.install = function (Vue) {
    Vue.component(MiniFormObject.name, MiniFormObject);
};

MetaEasyEdit.install = function (Vue) {
    Vue.component(MetaEasyEdit.name, MetaEasyEdit);
};

export {MiniFormField, MiniFormObject, MetaEasyEdit};
