import utils from '@/utils'

const children = [
    {
        path: 'meta-data',
        name: 'Metadata',
        props: {R_fc: 'tcode_test'}, // TODO 元对象/元字段 主子表功能code暂时为 tcode_test, 正式需要修改
        component: 'meta/MetaDataManager'
    }, {
        path: 'meta-component',
        name: 'MetaComponent',
        props: {R_oc: "meta_component_instance"},
        component: 'meta/component/GlobalConfList'
    }, {
        path: 'global-conf',
        name: 'GlobalConf',
        props: (route) => ({R_cc: route.query.componentCode}),
        component: 'meta/component/GlobalConf'
    }, {
        path: 'meta-component-instance',
        name: 'MetaComponentInstance',
        props: {R_oc: "meta_component_instance"},
        component: 'meta/component-instance/InstanceConfList'
    }, {
        path: 'instance-conf',
        name: 'InstanceConf',
        props: (route) => ({R_cc: route.query.componentCode, R_oc: route.query.objectCode}),
        component: 'meta/component-instance/InstanceConf'
    }, {
        path: 'form-builder',
        name: 'FormBuilder',
        props: (route) => ({R_oc: route.query.objectCode}),
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
        props: (route) => ({R_fc: route.query.featureCode, R_oc: route.query.objectCode}),
        component: 'template/SingleGridTmpl'
    }, {
        path: 'ms-table',
        name: 'msTable',
        props: (route) => ({R_fc: route.query.featureCode}),
        component: 'template/MasterSlaveTableTmpl'
    }, {
        path: 'form',
        name: 'Form',
        props: (route) => ({R_oc: route.query.objectCode}),
        component: 'template/FormTmpl'
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
