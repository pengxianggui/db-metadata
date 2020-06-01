import Vue from 'vue'
import Router from 'vue-router'
import staticRoutes from './staticRoutes'
import metaRoutes from './metaRoutes'

Vue.use(Router);

const routes = [
    ...staticRoutes,
    ...metaRoutes
];

const router = new Router({
    // model: 'history', // hash or history
    routes: routes
});

export default router
