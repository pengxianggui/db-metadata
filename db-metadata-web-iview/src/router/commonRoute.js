import utils from '@/utils'

const children = [
    {
        path: 'meta-data',
        name: 'Metadata',
        component: 'meta/MetaDataManager'
    }, {
        path: 'meta-component',
        name: 'MetaComponent',
        component: 'meta/component/GlobalConfList'
    }, {
        path: 'global-conf',
        name: 'GlobalConf',
        component: 'meta/component/GlobalConf'
    }, {
        path: 'meta-component-instance',
        name: 'MetaComponentInstance',
        component: 'meta/component-instance/InstanceConfList'
    }, {
        path: 'instance-conf',
        name: 'InstanceConf',
        component: 'meta/component-instance/InstanceConf'
    }, {
        path: 'form-builder',
        name: 'FormBuilder',
        component: 'meta/form-builder'
    }, {
        path: 'objects',
        name: 'objects',
        component: 'template/ObjectsTmpl'
    }, {
        path: 'demo',
        name: 'Demo',
        component: 'demo/DemoMain'
    }, {
        path: 'table',
        name: 'table',
        component: 'template/SingleGridTmpl'
    }, {
        path: 'ms-table',
        name: 'msTable',
        component: 'template/MasterSlaveTableTmpl'
    }, {
        path: 'form',
        name: 'Form',
        component: 'template/FormTmpl'
    }, {
        path: 'tree-table',
        name: 'treeTable',
        component: 'template/TreeTableTmpl'
    }
];

const commonRoute = [
    {
        path: '/',
        name: 'Main',
        redirect: '/main'
    }, {
        path: '/main',
        name: 'main',
        component: 'Main',
        redirect: '/main/meta-data',
        children: children
    },
];

export let globalRoute = children.map(route => {
    let item = utils.deepClone(route);
    item.path = '/' + route.path;
    item.name = 'G_' + route.name;
    return item;
});
commonRoute.unshift(...globalRoute);

export default commonRoute
