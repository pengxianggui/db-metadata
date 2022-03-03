import MetaLayout from "./MetaLayout";
import MetaHeader from "./MetaHeader";
import MetaMenu from "./MetaMenu";
import MetaMain from "./MetaMain";

export default {
    install(Vue) {
        Vue.component(MetaLayout.name, MetaLayout)
        Vue.component(MetaHeader.name, MetaHeader)
        Vue.component(MetaMenu.name, MetaMenu)
        Vue.component(MetaMain.name, MetaMain)
    }
}

export {
    MetaLayout,
    MetaHeader,
    MetaMenu,
    MetaMain
}
