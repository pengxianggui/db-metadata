import Vue from 'vue'
import Router from 'vue-router'
import {user} from '@/../package/index'

Vue.use(Router);

export const routes = [
    {
        path: '/',
        hidden: false,
        component: () => import('@/layout'),
        meta: {title: '', icon: 'dashboard', order: -99999}, // order是对菜单的排序
        children: [
            {
                name: 'Dashboard',
                path: '/dashboard',
                component: () => import('../view/Dashboard'),
                meta: {title: '首页', icon: 'el-icon-menu', order: -99999},
                props: {oc: 'meta_dict'}
            }
        ]
    },
    {
        name: 'WorkSpace',
        path: '/workspace',
        hidden: true,
        component: () => import('@/components/demo/WorkSpace')
    },
    {
        name: 'Route1',
        path: '/route1',
        component: () => import('@/layout'),
        meta: {title: '路由1', order: 0, icon: 'more'},
        children: [
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
        name: 'Page404',
        path: '*',
        component: () => import('@/view/404'),
        meta: {order: Number.MAX_VALUE},
        hidden: true
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

export default router
