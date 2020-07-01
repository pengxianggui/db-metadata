import Main from '@/components/Main'
import utils from '@/../package/utils'

const jumpOut = [
    {
        path: 'instance-conf-edit',
        label: "组件实例编辑",
        hidden: true,
        name: 'InstanceConfEdit',
        component: () => import('@/../package/meta/component-instance/InstanceConfEdit')
    }
];

const routes = [
    {
        path: '/main',
        name: 'main',
        label: '平台维护',
        icon: "el-icon-s-tools",
        component: Main,
        redirect: '/main/meta-data',
        children: [
            {
                path: 'meta-data',
                name: 'Metadata',
                label: "元数据管理",
                icon: "el-icon-warning",
                component: () => import('@/../package/meta/MetaDataManager')
            }, {
                path: 'feature',
                name: 'Feature',
                label: '功能维护',
                props: {
                    oc: 'meta_feature'
                },
                icon: 'el-icon-warning-outline',
                component: () => import('@/../package/template/SingleGridTmpl')
            }, {
                path: 'meta-component',
                name: 'MetaComponent',
                label: "组件全局配置",
                icon: "el-icon-star-off",
                component: () => import('@/../package/meta/component/GlobalConfList')
            }, {
                path: 'global-conf',
                name: 'GlobalConf',
                label: "组件全局配置",
                icon: "el-icon-star-off",
                hidden: true,
                component: () => import('@/../package/meta/component/GlobalConf')
            }, {
                path: 'meta-component-instance',
                name: 'MetaComponentInstance',
                label: "组件实例配置",
                icon: "el-icon-star-on",
                component: () => import('@/../package/meta/component-instance/InstanceConfList')
            }, {
                path: 'instance-conf-new',
                name: 'InstanceConfNew',
                label: "组件实例配置",
                icon: "el-icon-star-off",
                hidden: true,
                component: () => import('@/../package/meta/component-instance/InstanceConfNew')
            }, {
                path: 'form-builder',
                name: 'FormBuilder',
                label: "表单构建",
                icon: "el-icon-s-order",
                component: () => import('@/../package/meta/form-builder')
            }, {
                path: 'meta-conf',
                name: 'MetaConf',
                label: 'MetaConf',
                icon: 'el-icon-s-tools',
                props: {
                    oc: 'meta_conf'
                },
                component: () => import('@/../package/template/SingleGridTmpl')
            }, {
                path: 'meta-dict',
                name: 'MetaDict',
                label: '字典',
                icon: 'el-icon-collection',
                props: {
                    oc: 'meta_dict'
                },
                component: () => import('@/../package/template/SingleGridTmpl')
            }, {
                path: 'meta-exception',
                name: 'MetaException',
                label: '异常',
                icon: 'el-icon-warning',
                props: {
                    oc: 'meta_exception'
                },
                component: () => import('@/../package/template/SingleGridTmpl')
            }, {
                path: 'meta-version',
                name: 'MetaVersion',
                label: 'DbVersion',
                icon: 'el-icon-paperclip',
                props: {
                    fc: 'DbVersionSingleGrid'
                },
                component: () => import('@/../package/template/SingleGridTmpl')
            }, {
                path: 'objects',
                name: 'objects',
                label: "动态元对象",
                icon: "el-icon-menu",
                component: () => import('@/../package/template/ObjectsTmpl')
            }, {
                path: 'demo',
                name: 'Demo',
                label: "组件概览",
                icon: "el-icon-menu",
                component: () => import('@/components/demo/DemoMain')
            },
            ...jumpOut
        ]
    }
];

export let globalRoute = jumpOut.map(route => {
    let item = utils.deepClone(route);
    item.path = '/' + route.path;
    item.name = 'G_' + route.name; // 避免重名
    return item;
});
routes.unshift(...globalRoute);

export default routes;
