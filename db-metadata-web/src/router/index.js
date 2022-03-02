import Vue from 'vue'
import Router from 'vue-router'
import {MetaLayout} from '@/../package/index'

Vue.use(Router);

export const menus = [
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
        component: MetaLayout,
        redirect: '/dashboard',
        children: [
            {
                name: 'Route1-1',
                path: 'route1-1',
                component: () => import('@/../package/template/SingleGridTmpl'),
                meta: {title: '路由1-1', order: 1},
                props: {oc: 'meta_dict'}
            },
            {
                name: 'Route1-2',
                path: 'route1-2',
                component: () => import('@/../package/template/SingleGridTmpl'),
                meta: {title: '路由1-2', order: 0, disable: true}, // 对编程路由而言，disable是不生效的
                props: {oc: 'meta_dict'}
            },
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
