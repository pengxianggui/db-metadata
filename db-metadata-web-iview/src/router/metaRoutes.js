import Main from '@/components/Main'
import utils from "@/utils";

const jumpOut = [
    {
        path: 'instance-conf-edit',
        name: 'InstanceConfEdit',
        component: () => import('@/components/meta/component-instance/InstanceConfEdit')
    }
];

const routes = [
    {
        path: '/',
        name: 'Main',
        redirect: '/main'
    }, {
        path: '/main',
        name: 'main',
        component: Main,
        redirect: '/main/meta-data',
        children: [
            {
                path: 'meta-data',
                name: 'Metadata',
                component: () => import('@/components/meta/MetaDataManager')
            }, {
                path: 'meta-component',
                name: 'MetaComponent',
                component: () => import('@/components/meta/component/GlobalConfList')
            }, {
                path: 'global-conf',
                name: 'GlobalConf',
                component: () => import('@/components/meta/component/GlobalConf')
            }, {
                path: 'meta-component-instance',
                name: 'MetaComponentInstance',
                component: () => import('@/components/meta/component-instance/InstanceConfList')
            }, {
                path: 'instance-conf-new',
                name: 'InstanceConfNew',
                component: () => import('@/components/meta/component-instance/InstanceConfNew')
            }, {
                path: 'form-builder',
                name: 'FormBuilder',
                component: () => import('@/components/meta/form-builder')
            }, {
                path: 'objects',
                name: 'objects',
                component: () => import('@/components/template/ObjectsTmpl')
            }, {
                path: 'demo',
                name: 'Demo',
                component: () => import('@/components/demo/DemoMain')
            }, {
                path: 'table',
                name: 'table',
                component: () => import('@/components/template/SingleGridTmpl')
            }, {
                path: 'ms-table',
                name: 'msTable',
                component: () => import('@/components/template/MasterSlaveTableTmpl')
            }, {
                path: 'form',
                name: 'Form',
                component: () => import('@/components/template/FormTmpl')
            }, {
                path: 'tree/in',
                name: 'treeInTable',
                component: () => import('@/components/template/TreeSingleGridTmpl')
            }, {
                path: 'tree/and',
                name: 'treeAndTable',
                component: () => import('@/components/template/TreeTableTmpl')
            }, {
                path: 'list-table',
                name: 'listTable',
                component: () => import('@/components/template/DataListTableTmpl')
            }, {
                path: 'table-form',
                name: 'tableForm',
                component: () => import('@/components/template/TableFormTmpl')
            }, {
                path: 'tree-form',
                name: 'treeForm',
                component: () => import('@/components/template/TreeFormTmpl')
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
