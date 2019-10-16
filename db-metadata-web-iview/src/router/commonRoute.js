import Main from '@/components/Main'
import MasterSlaveTableTmpl from "../components/template/MasterSlaveTableTmpl";
import TableTmpl from "../components/template/TableTmpl";

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
        path: '/table-tmpl',
        name: 'TableTmpl',
        component: TableTmpl
    }
]
export default commonRoute
