import Page401 from "../../page/error/Page401";
import Page404 from "../../page/error/Page404";

const app = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('../../page/Login'),
        meta: {
            need_permit: false
        }
    }
]
const error = [
    {
        path: '/401',
        name: 'Page401',
        meta: {
            title: "WITHOUT PERMISSION",
            need_permit: false
        },
        component: Page401
    },
    {
        path: '*',
        name: 'Page404',
        meta: {
            title: "NOT FOUND",
            need_permit: false
        },
        component: Page404
    }
]

export default [
    ...app,
    ...error
]
