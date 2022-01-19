import NavMenu from "./src/NavMenu";
import MenuItem from "./src/MenuItem";

NavMenu.install = function (Vue) {
    Vue.component(NavMenu.name, NavMenu)
    Vue.component(MenuItem.name, MenuItem)
}

export default NavMenu
