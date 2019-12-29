import JsonBox from './src/JsonBox'

JsonBox.install = function (Vue) {
    Vue.component(JsonBox.name, JsonBox);
};

export default JsonBox;