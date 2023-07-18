import MetaDataManager from "../../meta/meta-data-manager/MetaDataManager";
import MetaFeatureList from "../../meta/feature";
import GlobalConfList from "../../meta/global-component/GlobalConfList";
import GlobalConf from "../../meta/global-component/GlobalConf";
import InstanceConfList from "../../meta/instance-component/InstanceConfList";
import RouterManager from "../../meta/route/RouterManager";
import MenuManager from "../../meta/menu/MenuManager";
import ProfileMenuManager from "../../meta/profile-menu/ProfileMenuManager";
import InstanceConfEditor from "../../meta/instance-component/InstanceConfEditor";
import ApiResourceList from "../../page/api-resource/ApiResourceList";
import AuthList from "../../page/auth/AuthList";
import SnippetList from "../../page/snippet/SnippetList.vue";

const routes = [
    {
        path: '/meta/manager',
        name: 'MetaDataManager',
        meta: {
            title: "元数据管理",
            icon: "el-icon-warning",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_data']
        },
        component: MetaDataManager
    }, {
        path: '/meta/feature',
        name: 'MetaFeatureList',
        meta: {
            title: '功能维护',
            icon: 'el-icon-warning-outline',
            noCache: false,
            need_permit: true,
            auths: ['route:meta_feature']
        },
        component: MetaFeatureList
    }, {
        path: '/meta/global-conf-list',
        name: 'GlobalConfList',
        meta: {
            title: "组件默认配置",
            icon: "el-icon-star-off",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_component']
        },
        component: GlobalConfList
    }, {
        path: '/meta/global-conf',
        name: 'GlobalConf',
        meta: {
            title: "组件默认配置-编辑",
            icon: "el-icon-star-off",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_component:edit']
        },
        hidden: true,
        component: GlobalConf
    }, {
        path: '/meta/instance-conf-list',
        name: 'InstanceConfList',
        meta: {
            title: "组件实例配置",
            icon: "el-icon-star-on",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_component_instance']
        },
        component: InstanceConfList
    }, {
        path: '/meta/instance-conf-edit',
        component: InstanceConfEditor,
        name: 'InstanceConfEditor',
        meta: {
            title: '组件实例配置-编辑',
            icon: 'edit',
            noCache: false,
            need_permit: true,
            auths: ['route:meta_component_instance:edit']
        },
        hidden: true,
        props: (route) => ({
            ic: route.query.instanceCode,
            fc: route.query.fieldCode
        })
    }, {
        path: '/meta/router',
        name: 'RouterManager',
        meta: {
            title: "路由维护",
            icon: "el-icon-star-off",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_router']
        },
        hidden: false,
        component: RouterManager
    }, {
        path: '/meta/menu',
        name: 'MenuManager',
        meta: {
            title: "菜单维护",
            icon: "el-icon-star-off",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_menu']
        },
        hidden: false,
        component: MenuManager
    }, {
        path: '/meta/profile-menu',
        name: 'ProfileMenuManager',
        meta: {
            title: "Profile菜单维护",
            icon: "el-icon-star-off",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_profile_menu']
        },
        hidden: false,
        component: ProfileMenuManager
    }, {
        path: '/meta/snippet',
        name: 'SnippetList',
        meta: {
            title: '代码片段',
            icon: 'code',
            noCache: false,
            need_permit: true,
            auths: ['route:meta_snippet']
        },
        hidden: false,
        component: SnippetList
    }, {
        path: '/meta/api-resource',
        name: 'ApiResourceList',
        meta: {
            title: "接口资源",
            icon: "api",
            noCache: false,
            need_permit: true,
            auths: ['route:meta_api_resource']
        },
        hidden: false,
        component: ApiResourceList
    }, {
        path: '/meta/meta-auth',
        name: 'AuthList',
        meta: {
            title: '权限配置',
            icon: 'el-icon-s-tools',
            noCache: false,
            need_permit: true,
            auths: ['route:meta_auth']
        },
        component: AuthList
    }
]

export default function () {
    return routes
}
