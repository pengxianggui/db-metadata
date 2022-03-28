// <img :src="$withBase('/foo.png')" alt="foo"

module.exports = {
    title: 'Db-Metadata 教程 (v2.2)',
    description: '手册将介绍Db-Metadata研发初衷、设计思路、核心概念、使用指南和扩展方法',
    head: [
        ['link', {rel: 'shortcut icon', type: 'image/x-icon', href: '/logo.png'}],
        // ['meta', {rel: 'keywords', href: 'dbmeta'}]
    ],
    repo: 'https://gitlab.asoco.com.cn/hthjAssets/db-metadata',  //仓库地址
    plugins: ['demo-container'], // 配置插件
    themeConfig: {
        lastUpdated: 'Last Updated', // string | boolean
        logo: '/logo.svg',
        nav: [
            {text: '教程', link: '/guide/introduce'},
            {text: '组件库介绍', link: '/component/'},
            {text: '演示环境', link: 'http://dev-dbmeta.asoco.com.cn'},
            {text: '关于我', link: '/about'}
        ],
        // displayAllHeaders: true,
        sidebar: {
            '/guide/': [
                {
                    title: '指南',   // 必要的
                    collapsable: false, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: [
                        'introduce',
                        'purpose',
                        'design',
                        'coreConcept',
                        'quickStart',
                        'createObject',
                        'createFeature',
                        'adjustConfig',
                        "use",
                        "toBeContinue"
                    ]
                },
                {
                    title: 'Dev-Todo',
                    collapsable: false, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    path: '/guide/dev-todo',
                    children: [

                    ]
                }
            ],
            '/component/': [
                '',
                {
                    title: '域组件',   // 必要的
                    path: '/component/field/',
                    // collapsable: true, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: [
                        'field/textbox',
                        'field/textareabox',
                        'field/numbox',
                        'field/passbox',
                        'field/boolbox',
                        'field/radiobox',
                        'field/checkbox',
                        'field/dropdownbox',
                        'field/timebox',
                        'field/datebox',
                        'field/datetimebox',
                        'field/filebox',
                        'field/imgbox',
                        'field/jsonbox',
                        'field/miniformbox',
                        'field/findbox',
                        'field/regionbox',
                        'field/richtextbox'
                    ]
                },
                {
                    title: '容器组件',   // 必要的
                    path: '/component/view/',
                    // collapsable: true, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: [
                        'view/formview',
                        'view/searchview',
                        'view/tableview',
                        'view/tabletreeview',
                        'view/treeview',
                    ]
                },
                {
                    title: '模板组件',   // 必要的
                    path: '/component/template/',
                    // collapsable: true, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: [
                        'template/SingleGridTmpl',
                        'template/TreeSingleGridTmpl',
                        'template/MasterSlaveTableTmpl',
                        'template/TreeTableTmpl'
                    ]
                }
            ]
        }
    }
}
