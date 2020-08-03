import NavMenu from "./src/NavMenu";

NavMenu.install = function (Vue) {
    Vue.component(NavMenu.name, NavMenu)
}

export default NavMenu