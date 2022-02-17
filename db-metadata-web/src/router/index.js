import Vue from 'vue'
import Router from 'vue-router'
import {user, MetaMain} from '@/../package/index'

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
        title: '菜单1',
        icon: 'el-icon-menu',
        hidden: false,
        disable: false,
        order: 1,
        children: [
            {
                path: '/admin/route1-1',
                title: '菜单1-1',
                icon: 'el-icon-menu',
                hidden: false,
                disable: true,
                order: 0
            },
            {
                path: '/admin/route1-2',
                title: '菜单1-2',
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
        redirect: '/meta/meta-data',
        children: [
            {
                path: '/admin',
                name: 'Admin',
                component: MetaMain,
                children: [
                    {
                        name: 'Dashboard',
                        path: '/dashboard',
                        component: () => import('../view/Dashboard'),
                        meta: {title: '首页', icon: 'el-icon-menu', order: -99999},
                        props: {oc: 'meta_dict'}
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
                    }
                ]
            }, {
                path: '/index',
                name: 'Index',
                component: () => import('@/view/Index')
            },
            {
                name: 'About',
                path: '/about',
                component: () => import('@/view/About')
            },
        ]
    },
    {
        name: 'WorkSpace',
        path: '/workspace',
        component: () => import('@/components/demo/WorkSpace')
    },
    {
        name: 'Page404',
        path: '*',
        component: () => import('@/view/404')
    }
];

const router = new Router({
    // model: 'history', // hash or history
    routes: routes
});

router.beforeEach(async (to, from, next) => {
    user.setRoles(["ROOT"]) // 异步获取角色并设置
    next()
})

// console.log(router.getRoutes())
export default router
