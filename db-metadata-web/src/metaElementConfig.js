import axios from "@/axios";
import router, {menus} from "@/router";
import Route1_2 from "@/views/Route1_2";

export default {
    // 必要的配置
    axios: axios, // axios实例(必须)
    router: router, // 路由实例(必须)

    // 以下为非必要配置
    menus: menus, // 编程菜单
    // routerInterceptor: { // 路由守卫
    //     enable: true, // 开启内置的路由守卫。开启后，由MetaElement负责值守路由，并对路由鉴权。如果关闭，你需要自行维持路由鉴权，并维持用户状态。
    // },
    // axiosInterceptor: { // axios拦截器
    //     enable: true
    // },
    // layout: MyLayout, // 布局组件(可选，空则默认。若为默认，则编程路由需要自行使用MetaElement中导出的MetaLayout)
    components: [Route1_2], // 如果你的组件需要能够被动态路由配置时选中，那么需要传入此component中
    // restUrl: {}, // rest请求, 用于覆盖内部rest请求url. 基本无需配置。参考【内置接口地址】
    // routeUrl: {}, // 用于覆盖内置的路由地址。参考【内置路由列表】
    // access: { // 访问权限配置
    //     root: '0' // ROOT用户的id, 默认就是0。
    // },
    // theme: { // 默认的主题设置。如果是下面的配置，可以不提供。需要覆盖什么，就提供什么（优先级低于用户自主配置的）
    //     layout: 'row',
    //     themeColor: 'light', // light/dark。下面颜色的属性会覆盖此light代表的属性配置
    //     header: {
    //         textColor: '#409EFF',
    //         backgroundColor: '#ffffff'
    //     },
    //     menu: {
    //         textColor: '#303133',
    //         activeTextColor: '#409EFF',
    //         backgroundColor: '#ffffff',
    //         uniqueOpened: false
    //     },
    //     tag: {
    //         show: true,
    //         textColor: '#ffffff',
    //         backgroundColor: '#409EFF'
    //         activeTextColor: '#ffffff',
    //         activeBackgroundColor: '#409EFF'
    //     }
    // }
}
