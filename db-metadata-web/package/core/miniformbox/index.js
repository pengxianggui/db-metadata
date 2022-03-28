import MiniFormBox from './src/MiniFormBox'
import MiniForm from './src/core'

MiniFormBox.install = function (Vue) {
    Vue.component(MiniFormBox.name, MiniFormBox);
    Vue.component(MiniForm.name, MiniForm);
};

export default MiniFormBox;
