import Page401 from "../../page/error/Page401";
import Page404 from "../../page/error/Page404";

export default [
    {
        path: '/401',
        name: 'Page401',
        meta: {
            title: "WITHOUT PERMISSION"
        },
        component: Page401
    },
    {
        path: '*',
        name: 'Page404',
        meta: {
            title: "NOT FOUND"
        },
        component: Page404
    }
]
