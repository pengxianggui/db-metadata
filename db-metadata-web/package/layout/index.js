import MetaHeader from "./MetaHeader";

import MetaLayout from "./MetaLayout";
import MetaMain from "./MetaMain";

export default {
    install(Vue) {
        Vue.component(MetaHeader.name, MetaHeader)

        Vue.component(MetaLayout.name, MetaLayout)
        Vue.component(MetaMain.name, MetaMain)
    }
}

export {
    MetaHeader,
    MetaLayout
}
