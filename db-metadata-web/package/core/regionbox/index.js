import RegionBox from "./src/RegionBox";

RegionBox.install = function (Vue, opts = {}) {
    Vue.component(RegionBox.name, RegionBox)
}

export default RegionBox