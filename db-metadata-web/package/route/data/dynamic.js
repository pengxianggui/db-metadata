import {restUrl} from "../../constant/url";
import exchange from "../exchange";

export default function (Vue, axios, opts) {
    return new Promise((resolve, reject) => {
        axios.get(restUrl.ROUTE_DATA).then(resp => {
            const {data: routes} = resp
            const dynamicRoutes = exchange(Vue, opts, routes) // 装配组件, http请求到的路由数据component只是组件名, 需要转换为真实的组件
            resolve(dynamicRoutes)
        }).catch(err => {
            reject(err)
        })
    })
}
