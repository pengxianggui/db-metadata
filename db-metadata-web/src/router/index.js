import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import metaRoutes from './metaRoutes'
import templateRoutes from './templateRoutes'
import staticRoutes from './staticRoutes'
import {user} from '@/../package/index'

Vue.use(Router);

export const routes = [
    {
        path: '/',
        component: Main,
        redirect: '/dashboard',
        hidden: true,
        children: [
            {
                path: 'dashboard',
                component: () => import('@/../package/meta/MetaDataManager'),
                name: 'Dashboard',
                label: 'Dashboard'
            },
        ]
    },
    ...metaRoutes,
    ...templateRoutes,
    ...staticRoutes,
    // ...dynamicRoutes
];

Vue.component("Main", Main) // 全局注册的 容器组件 才能在 动态路由中被正确定位（组件名 => 组件 的定位）

const router = new Router({
    // model: 'history', // hash or history
    routes: routes
    // 下面这块内容尝试当路由全部由MetaData后端动态数据生成时, 自定义硬编码能否具备足够的自由度。 结果是乐观的
    // routes: [
    //     {
    //         "path": "/workspace",
    //         "name": "workspace",
    //         "component": () => import('@/components/demo/WorkSpace')
    //     }, {
    //         "path": "/main",
    //         "name": "main",
    //         "component": Main,
    //         "children": [{
    //             "path": "test",
    //             "name": "TEST",
    //             "component": Vue.component("test", {
    //                 template: `<h1>Test Component!</h1>`
    //             })
    //         }]
    //     }
    // ]
});

router.beforeEach(async (to, from, next) => {
    user.setRoles(["ROOT"]) // 异步获取角色并设置
    next()
})

export default router
