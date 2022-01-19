import FindBox from './src/FindBox'
import FindPanel from "./src/FindPanel";

FindBox.install = function (Vue) {
    Vue.component(FindBox.name, FindBox);
    Vue.component(FindPanel.name, FindPanel);
};

export default FindBox;
