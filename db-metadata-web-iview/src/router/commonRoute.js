import Main from '@/components/Main'
import MasterSlaveTableTmpl from "../components/template/MasterSlaveTableTmpl";
import MetaManager from "../components/meta/MetaManager";

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
    }
]
export default commonRoute
