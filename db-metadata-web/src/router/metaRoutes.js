import Main from '@/components/Main'
import utils from '@/../package/utils'
import MetaRoutes from '@/../package/route'

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
            ...(MetaRoutes.map(r => {
                r.icon = r.meta.icon;
                r.label = r.meta.title;
                return r;
            })),
            {
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
            }
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
