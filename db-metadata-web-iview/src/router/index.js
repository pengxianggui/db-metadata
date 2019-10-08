import Vue from 'vue'
import Router from 'vue-router'
import commonRoutes from './commonRoute'

Vue.use(Router)

const router = new Router({
    // model: 'history',
    routes: commonRoutes
})

export default router
