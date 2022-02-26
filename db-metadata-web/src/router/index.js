import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router);

export const menus = [
    {
        path: '/dashboard',
        title: '首页',
        icon: 'el-icon-menu',
        hidden: false,
        disable: false,
        order: -99999
    },
    {
        path: '/admin/route1',
        title: '可编程菜单1',
        icon: 'el-icon-menu',
        hidden: false,
        disable: false,
        order: 1,
        children: [
            {
                path: '/route1-1',
                title: '可编程菜单1-1',
                icon: 'el-icon-menu',
                hidden: false,
                disable: true,
                order: 0
            },
            {
                path: '/route1-2',
                title: '可编程菜单1-2',
                icon: 'el-icon-menu',
                hidden: false,
                disable: false,
                order: 1
            }
        ]
    }
]

export const routes = [
    {
        path: "/",
        component: () => import('@/layout'),
        redirect: '/dashboard',
        children: [
            {
                name: 'Dashboard',
                path: '/dashboard',
                component: () => import('../view/Dashboard'),
                meta: {title: '首页', icon: 'el-icon-menu', order: -99999}
            },
            {
                name: 'Route1-1',
                path: 'route1-1',
                component: () => import('@/../package/template/SingleGridTmpl'),
                meta: {title: '路由1-1', icon: 'more', order: 1},
                props: {oc: 'meta_dict'}
            },
            {
                name: 'Route1-2',
                path: 'route1-2',
                component: () => import('@/../package/template/SingleGridTmpl'),
                meta: {title: '路由1-2', icon: 'more', order: 0},
                props: {oc: 'meta_dict'}
            },
            {
                path: '/login',
                name: 'Login',
                component: () => import('@/view/Login'),
                meta: {
                    need_permit: false
                }
            }
        ]
    },
    {
        name: 'WorkSpace',
        path: '/workspace',
        component: () => import('@/components/demo/WorkSpace')
    }
];

const router = new Router({
    // model: 'history', // hash or history
    routes: routes
});

export default router
