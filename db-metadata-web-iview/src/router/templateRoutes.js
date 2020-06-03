import Main from '@/components/Main'

export default [
    {
        path: '/main',
        name: 'business',
        label: '模板路由',
        icon: "el-icon-s-tools",
        component: Main,
        hidden: true,
        children: [
            {
                path: 'table',
                name: 'table',
                hidden: true,
                component: () => import('@/components/template/SingleGridTmpl')
            }, {
                path: 'ms-table',
                name: 'msTable',
                hidden: true,
                component: () => import('@/components/template/MasterSlaveTableTmpl')
            }, {
                path: 'form',
                name: 'Form',
                hidden: true,
                component: () => import('@/components/template/FormTmpl')
            }, {
                path: 'tree/in',
                name: 'treeInTable',
                hidden: true,
                component: () => import('@/components/template/TreeSingleGridTmpl')
            }, {
                path: 'tree/and',
                name: 'treeAndTable',
                hidden: true,
                component: () => import('@/components/template/TreeTableTmpl')
            }, {
                path: 'list-table',
                name: 'listTable',
                hidden: true,
                component: () => import('@/components/template/DataListTableTmpl')
            }, {
                path: 'table-form',
                name: 'tableForm',
                hidden: true,
                component: () => import('@/components/template/TableFormTmpl')
            }, {
                path: 'tree-form',
                name: 'treeForm',
                hidden: true,
                component: () => import('@/components/template/TreeFormTmpl')
            }
        ]
    }
]