import Main from '@/components/Main'
import MasterSlaveTableTmpl from "../components/template/MasterSlaveTableTmpl";
import MetaManager from "../components/meta/MetaManager";
import MetaConf from "../components/meta/MetaConf";
import MetaObject from "../components/meta/MetaObject";
import DemoMain from "../components/demo/DemoMain";

const commonRoute = [
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
                component: MasterSlaveTableTmpl
            },{
                path: 'meta-manager',
                name: 'MetaManager',
                component: MetaManager
            },{
                path: 'meta-object',
                name: 'MetaObject',
                component: MetaObject
            },{
                path: 'meta-conf',
                name: 'MetaConf',
                component: MetaConf
            },{
                path: 'demo',
                name: 'Demo',
                component: DemoMain
            }
        ]
    },
]
export default commonRoute
