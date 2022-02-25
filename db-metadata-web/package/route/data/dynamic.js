import {restUrl} from "../../constant/url";
import exchange from "../exchange";
import MetaMain from "../../layout/admin";

const route = {
    path: '/main',
    name: 'MetaMain',
    hidden: true,
    component: MetaMain,
    children: []
}

export default function (axios) {
    return new Promise((resolve, reject) => {
        axios.get(restUrl.ROUTE_DATA).then(resp => {
            const {data: routes} = resp
            const dynamicRoutes = exchange(routes, MetaMain)
            route.children.push(...dynamicRoutes)
            resolve(new Array(route))
        }).catch(err => {
            reject(err)
        })
    })
}
