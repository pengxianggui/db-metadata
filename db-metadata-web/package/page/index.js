import Dashboard from "./Dashboard";
import Login from "./Login";
import Page401 from "./error/Page401";
import Page404 from "./error/Page404";

export default {
    install(Vue, ops) {
        Vue.component(Dashboard.name, Dashboard)
        Vue.component(Login.name, Login)
        Vue.component(Page401.name, Page401)
        Vue.component(Page404.name, Page404)
    }
}
