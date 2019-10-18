import Main from '@/components/Main'
import MasterSlaveTableTmpl from "../components/template/MasterSlaveTableTmpl";
import MetaManager from "../components/meta/MetaManager";
import MetaConf from "../components/meta/MetaConf";

const commonRoute = [
    {
        path: '/',
        name: 'Main',
        redirect: '/metadata'
    }, {
        path: '/demo',
        name: 'Demo',
        component: Main
    },{
        path: '/metadata',
        name: 'Metadata',
        component: MasterSlaveTableTmpl
    },{
        path: '/meta-manager',
        name: 'MetaManager',
        component: MetaManager
    },{
        path: '/meta-conf',
        name: 'MetaConf',
        component: MetaConf
    }
]
export default commonRoute
