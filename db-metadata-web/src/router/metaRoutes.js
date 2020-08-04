import Main from '@/components/Main'
import MetaRoutes from '@/../package/route'

const routes = [
    {
        path: '/main',
        name: 'main',
        label: '平台维护',
        icon: "el-icon-s-tools",
        component: Main,
        redirect: '/main/meta-data',
        children: [
            ...(MetaRoutes.inner.map(r => {
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
    },
    ...MetaRoutes.outer
];

export default routes;
