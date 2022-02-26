import {restUrl} from "../../constant/url";
import exchange from "../exchange";

export default function (axios, Layout) {
    return new Promise((resolve, reject) => {
        axios.get(restUrl.ROUTE_DATA).then(resp => {
            const {data: routes} = resp
            const dynamicRoutes = exchange(routes, Layout)
            resolve(dynamicRoutes)
        }).catch(err => {
            reject(err)
        })
    })
}
