import CodeBox from './src/CodeBox'

CodeBox.install = function (Vue) {
    Vue.component(CodeBox.name, CodeBox);
};

export default CodeBox;