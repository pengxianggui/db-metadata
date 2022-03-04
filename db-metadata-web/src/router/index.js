import Vue from 'vue'
import Router from 'vue-router'
import {MetaLayout} from '@/../package/index'

Vue.use(Router);

export const menus = [
    {
        path: '/route1',
        title: '编程菜单',
        icon: 'el-icon-menu',
        hidden: false,
        disable: false,
        order: 1,
        need_permit: false,
        children: [
            {
                path: '/route1-1',
                title: '禁用的编程菜单',
                icon: 'el-icon-menu',
                hidden: false,
                disable: true,
                order: 0,
                need_permit: false // 默认false。当直接指定auths时，就可以无需指定need_permit,会视作true。但如果指定了auths又声明了need_permit为false, 则不会鉴权
            },
            {
                path: '/route1-2',
                title: '可用的编程菜单',
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
                component: () => import('@/views/Route1_1'),
                meta: {title: '路由1-1', order: 1},
                props: {oc: 'meta_dict'}
            },
            {
                name: 'Route1-2',
                path: 'route1-2',
                component: () => import('@/views/Route1_2'),
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
