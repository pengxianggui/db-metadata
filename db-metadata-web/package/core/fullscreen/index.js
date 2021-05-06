import FullScreen from "./src/FullScreen";

FullScreen.install = function (Vue) {
    Vue.component(FullScreen.name, FullScreen)
}
export default FullScreen