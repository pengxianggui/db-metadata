import Vue from 'vue'
import Router from 'vue-router'
import Main from '@/components/Main'
import {URL} from '@/constant'
import utils from '@/utils'
import axios from 'axios'

Vue.use(Router);

const router = new Router({
    // model: 'history',
    routes: []
});

const routesKey = 'router';
let routes;

localStorage.removeItem(routesKey); // 调试阶段先每次清空

router.beforeEach((to, from, next) => {
    assembleProps(to);
    if (utils.isEmpty(routes)) {
        if (utils.isEmpty(getRoutesFromLocalStorage(routesKey))) {
            axios.get(URL.ROUTE_DATA).then(resp => {
                routes = resp.data;
                // routes = commonRoute;
                setRoutesToLocalStorage(routesKey, routes);
                routerGo(to, next);
            });
        } else {
            routes = getRoutesFromLocalStorage(routesKey);
            routerGo(to, next);
        }
    } else {
        next();
    }
});

function assembleProps(to) {
    to.matched.filter(route => {
        let props = route['props']['default'];
        if (utils.isEmpty(props)) return true;

        if (utils.isEmpty(props.R_fc)) {
            props['R_fc'] = to.query['featureCode'];
        }
        if (utils.isEmpty(props.R_cc)) {
            props['R_cc'] = to.query['componentCode'];
        }
        if (utils.isEmpty(props.R_oc)) {
            props['R_oc'] = to.query['objectCode'];
        }
        return true;
    });
}

function getRoutesFromLocalStorage(routesKey) {
    return JSON.parse(window.localStorage.getItem(routesKey));
}

function setRoutesToLocalStorage(routesKey, routes) {
    window.localStorage.setItem(routesKey, JSON.stringify(routes));
}

function routerGo(to, next) {
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
