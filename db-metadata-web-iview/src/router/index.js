import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import metaRoutes from './metaRoutes'
import templateRoutes from './templateRoutes'
import staticRoutes from './staticRoutes'
// import dynamicRoutes from '../../package/register/dynamic-router'

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

const router = new Router({
    // model: 'history', // hash or history
    routes: routes
});

export default router
