import PageSelector from "./src/PageSelector";

PageSelector.install = function (Vue, opts) {
    Vue.component(PageSelector.name, PageSelector)
}

export default PageSelector
