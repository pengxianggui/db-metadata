
const commonRoute = [
    {
        path: '/',
        name: 'Main',
        redirect: '/main'
    }, {
        path: '/main',
        name: 'main',
        component: () => import('@/components/Main'),
        redirect: '/main/meta-object',
        children: [
            {
                path: 'meta-data',
                name: 'Metadata',
                component: () => import('@/components/template/MasterSlaveTableTmpl')
            }, {
                path: 'meta-manager',
                name: 'MetaManager',
                component: () => import('@/components/meta/MetaManager')
            }, {
                path: 'meta-object',
                name: 'MetaObject',
                component: () => import('@/components/meta/MetaObject')
            }, {
                path: 'meta-component-instance-conf',
                name: 'MetaConf',
                component: () => import('@/components/meta/MetaComponentInstanceConf')
            }, {
                path: 'demo',
                name: 'Demo',
                component: () => import('@/components/demo/DemoMain')
            }, {
                path: 'table-data',
                name: 'TableData',
                component: () => import('@/components/meta/TableData')
            }, {
                path: 'change-log',
                name: 'ChangeLog',
                component: () => import('@/components/meta/ChangeLog')
            }, {
                path: 'meta-config',
                name: 'MetaConfig',
                component: () => import('@/components/meta/MetaConfig')
            }, {
                path: 'db-version',
                name: 'DbVersion',
                component: () => import('@/components/meta/DbVersion')
            }
        ]
    },
];
export default commonRoute
