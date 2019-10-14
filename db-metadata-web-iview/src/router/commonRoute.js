import Main from '@/components/Main'
import MasterSlaveTableTmpl from "../components/template/MasterSlaveTableTmpl";

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
    }
]
export default commonRoute
