import PopMenu from './src/PopMenu'

PopMenu.install = function (Vue) {
    Vue.component(PopMenu.name, PopMenu);
};

export default PopMenu;