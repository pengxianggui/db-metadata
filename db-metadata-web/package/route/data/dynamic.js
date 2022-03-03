import {restUrl} from "../../constant/url";
import exchange from "../exchange";

export default function (Vue, axios, Layout) {
    return new Promise((resolve, reject) => {
        axios.get(restUrl.ROUTE_DATA).then(resp => {
            const {data: routes} = resp
            const dynamicRoutes = exchange(Vue, routes, Layout)
            resolve(dynamicRoutes)
        }).catch(err => {
            reject(err)
        })
    })
}
