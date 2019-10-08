import Main from '@/components/Main'
import MasterSlaveComponent from "@/components/template/MasterSlaveComponent";

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
        component: MasterSlaveComponent
    }
]
export default commonRoute
