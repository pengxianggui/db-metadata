import cloneDeep from 'lodash/cloneDeep'

const children = [
    {
        path: 'meta-data',
        name: 'Metadata',
        props: {R_moc: "meta_object", R_soc: "meta_field", R_linkage: {m: "code", s: "object_code"}}, // m.code=s.object_code
        component: () => import('@/components/meta/MetaDataManager')
    }, {
        path: 'meta-component',
        name: 'MetaComponent',
        props: {R_oc: "meta_component_instance"},
        component: () => import('@/components/meta/meta-component/GlobalConfList')
    }, {
        path: 'global-conf',
        name: 'GlobalConf',
        props: (route) => ({R_cc: route.query.componentCode}),
        component: () => import('@/components/meta/meta-component/GlobalConf')
    }, {
        path: 'meta-component-instance',
        name: 'MetaComponentInstance',
        props: {R_oc: "meta_component_instance"},
        component: () => import('@/components/meta/meta-component-instance/InstanceConfList')
    }, {
        path: 'instance-conf',
        name: 'InstanceConf',
        props: (route) => ({R_cc: route.query.componentCode, R_oc: route.query.objectCode}),
        component: () => import('@/components/meta/meta-component-instance/InstanceConf')
    }, {
        path: 'form-builder',
        name: 'FormBuilder',
        props: (route) => ({R_oc: route.query.objectCode}),
        component: () => import('@/components/meta/form-builder')
    }, {
        path: 'table',
        name: 'table',
        props: (route) => ({R_oc: route.query.objectCode}),
        component: () => import('@/components/template/SingleGridTmpl')
    }, {
        path: 'ms-table',
        name: 'msTable',
        props: (route) => ({R_fc: route.query.featureCode}),
        component: () => import('@/components/template/MasterSlaveTableTmpl')
    }, {
    //     path: 'ChangeLog',
    //     name: 'ChangeLog',
    //     props: {R_oc: "change_log"},
    //     component: () => import('@/components/template/SingleGridTmpl')
    // }, {
    //     path: 'table',
    //     name: 'MetaConfig',
    //     props: {R_oc: "meta_config"},
    //     component: () => import('@/components/template/SingleGridTmpl')
    // }, {
    //     path: 'table',
    //     name: 'DbVersion',
    //     props: {R_oc: "db_version"},
    //     component: () => import('@/components/template/SingleGridTmpl')
    // }, {
    //     path: 'table',
    //     name: 'MetaDict',
    //     props: {R_oc: "meta_dict"},
    //     component: () => import('@/components/template/SingleGridTmpl')
    // }, {
    //     path: 'table',
    //     name: 'TestTable',
    //     props: {R_oc: "test_table"},
    //     component: () => import('@/components/template/SingleGridTmpl')
    // }, {
        path: 'form',
        name: 'Form',
        props: (route) => ({R_oc: route.query.objectCode}),
        component: () => import('@/components/template/FormTmpl')
    }, {
        path: 'demo',
        name: 'Demo',
        component: () => import('@/components/demo/DemoMain')
    }, {
        path: 'objects',
        name: 'objects',
        component: () => import('@/components/template/ObjectsTmpl')
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
        component: () => import('@/components/Main'),
        redirect: '/main/meta-data',
        children: children
    },
];

export let globalRoute = children.map(route => {
    let item = cloneDeep(route);
    item.path = '/' + route.path;
    item.name = 'G_' + route.name;
    return item;
});
commonRoute.unshift(...globalRoute);

export default commonRoute
