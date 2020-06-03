import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import staticRoutes from './staticRoutes'
import metaRoutes from './metaRoutes'
import templateRoutes from './templateRoutes'

Vue.use(Router);

const routes = [
    {
        path: '/',
        redirect: '/main/meta-data',
    },
    ...staticRoutes,
    ...metaRoutes,
    ...templateRoutes
];

const router = new Router({
    // model: 'history', // hash or history
    routes: routes
});

export default router
