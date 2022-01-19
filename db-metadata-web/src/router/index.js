import Vue from 'vue'
import Router from 'vue-router'
import {user} from '@/../package/index'
import Layout from '../layout'
import {addRoutes} from '@/../package/index'
import axios from '@/axios'

Vue.use(Router);

export const routes = [
    {
        path: '/',
        redirect: '/meta',
        hidden: true
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
        meta: {title: '路由1'},
        children: [
            {
                name: 'Route1-1',
                path: 'route1-1',
                component: () => import('@/../package/template/SingleGridTmpl'),
                meta: {title: '路由1-1', icon: 'more'}
            }
        ]
    }
];

Vue.component("Layout", Layout) // 全局注册的 容器组件 才能在 动态路由中被正确定位（组件名 => 组件 的定位）

const router = new Router({
    // model: 'history', // hash or history
    routes: routes
});

router.onReady(() => {
    addRoutes(router, axios, Layout)
    // addRoutes(router, axios)
})

router.beforeEach(async (to, from, next) => {
    user.setRoles(["ROOT"]) // 异步获取角色并设置
    next()
})

export default router
