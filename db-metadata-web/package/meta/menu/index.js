import MenuManager from './MenuManager'

MenuManager.install = function (Vue) {
    Vue.component(MenuManager.name, MenuManager)
}

export default MenuManager