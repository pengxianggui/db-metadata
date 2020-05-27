import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import utils from '@/utils'
import splitRoute from './splitRoute'
import routers from './routes'

Vue.use(Router);

const router = new Router({
    // model: 'history', // hash or history
    routes: routers
});

const routesKey = 'router';
let routes;
// setRoutesToLocalStorage(routesKey, null); // pxg_todo 调试阶段先每次清空

router.beforeEach((to, from, next) => {
    if (utils.isEmpty(routes)) {
        routes = getRoutesFromLocalStorage(routesKey);
        if (utils.isEmpty(routes)) {

            routes = splitRoute;
            setRoutesToLocalStorage(routesKey, routes);
            routerGo(to, next, routes);

            // axios.get(URL.ROUTE_DATA).then(resp => {
            //     routes = resp.data;
            //     setRoutesToLocalStorage(routesKey, routes);
            //     routerGo(to, next, routes);
            // });
        } else {
            routerGo(to, next, routes);
        }
    } else {
        next()
    }
});

function getRoutesFromLocalStorage(routesKey) {
    return JSON.parse(window.localStorage.getItem(routesKey));
}

function setRoutesToLocalStorage(routesKey, routes) {
    window.localStorage.setItem(routesKey, JSON.stringify(routes));
}

function routerGo(to, next, routes) {
    routes = dealWithRoutes(routes);
    router.addRoutes(routes);
    next({...to, replace: true});
}

function dealWithRoutes(routes) {
    const machiningRoutes = routes.filter(route => {
        if (!utils.isEmpty(route.component)) {
            let componentName = route.component;

            if (componentName === 'Main') {
                route.component = Main;
            } else {
                route.component = () => import('@/components/' + componentName);
            }
        }

        if (!utils.isEmpty(route.children)) {
            route.children = dealWithRoutes(route.children);
        }
        return true;
    });
    return machiningRoutes;
}

export default router
