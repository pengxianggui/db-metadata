// <img :src="$withBase('/foo.png')" alt="foo"


module.exports = {
    title: 'Db-Metadata指南',
    description: '手册将介绍Db-Metadata研发初衷、设计思路、核心概念、使用指南和扩展方法',
    head: [
        ['link', {rel: 'shortcut icon', type: 'image/x-icon', href: '/logo.png'}],
        // ['meta', {rel: 'keywords', href: 'dbmeta'}]
    ],
    themeConfig: {
        logo: '/logo.svg',
        nav: [
            {text: '教程', link: '/guide/introduce'},
            {text: '组件库', link: '/component/'},
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
                        'quickStart'
                    ]
                },
                {
                    title: '后端集成',
                    children: [
                        'server/',
                        'server/config',
                        'server/extension'
                    ],
                    initialOpenGroupIndex: -1 // 可选的, 默认值是 0
                },
                {
                    title: '前端集成',
                    children: [
                        'web/',
                        'web/config'
                    ],
                    initialOpenGroupIndex: -1 // 可选的, 默认值是 0
                },
            ],
            '/component/': [
                {
                    title: '域组件',   // 必要的
                    // collapsable: true, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: [
                        'field/',
                        'field/textbox',
                        'field/textareabox',
                    ]
                },
                {
                    title: '容器组件',   // 必要的
                    // collapsable: true, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: [
                        'view/'
                    ]
                },
                {
                    title: '模板组件',   // 必要的
                    // collapsable: true, // 可选的, 默认值是 true,
                    sidebarDepth: 1,    // 可选的, 默认值是 1
                    children: [
                        'template/'
                    ]
                },
            ]
        }
    }
}
