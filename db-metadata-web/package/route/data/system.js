import Redirect from "../redirect";

const app = [
    {
        path: '/__redirect/:path(.*)',
        hidden: true,
        meta: {
            need_permit: false
        },
        component: Redirect
    }
]

export default [
    ...app
]
