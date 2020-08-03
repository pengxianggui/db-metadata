const menus = [
    {
        "children": [
            {
                "children": [{
                    "index": "/main/test",
                    "title": "TEST",
                    "icon": "el-icon-edit"
                }],
                "icon": "el-icon-edit",
                "title": "元数据管理",
                "hidden": false,
                "disable": false,
                "index": "/main/ms-table?featureCode=meta_manager"
            },
            {
                "children": [],
                "icon": "el-icon-s-custom",
                "title": "钉钉用户",
                "hidden": false,
                "disable": false,
                "index": "/main/ding-user?featureCode=dd_user_menu"
            },
            {
                "children": [],
                "icon": "el-icon-menu",
                "title": "鹊桥用户",
                "hidden": false,
                "disable": false,
                "index": "/main/magpie-user?featureCode=mb_user_menu"
            },
            {
                "children": [],
                "icon": "el-icon-s-custom",
                "title": "Gitlab用户",
                "hidden": false,
                "disable": false,
                "index": "/main/gitlab-user?featureCode=gitlab_user"
            },
            {
                "children": [],
                "icon": "el-icon-s-custom",
                "title": "Jira用户",
                "hidden": false,
                "disable": false,
                "index": "/main/jira-user?featureCode=jira_user_menu"
            },
            {
                "children": [],
                "icon": "el-icon-s-help",
                "title": "Jira项目",
                "hidden": false,
                "disable": false,
                "index": "/main/jira-project?featureCode=jira_project_menu"
            },
            {
                "children": [],
                "icon": "el-icon-s-goods",
                "title": "Gitlab项目",
                "hidden": false,
                "disable": false,
                "index": "/main/table?featureCode=gitlab_project_menu"
            },
            {
                "children": [],
                "icon": "el-icon-menu",
                "title": "测试表",
                "hidden": false,
                "disable": false,
                "index": "/main/table?featureCode=test_table_menu"
            },
            {
                "children": [],
                "icon": "el-icon-message-solid",
                "title": "v_one_view_11",
                "hidden": false,
                "disable": false,
                "index": "/main/table?featureCode=v_one_view_11"
            },
            {
                "children": [],
                "icon": "el-icon-s-data",
                "title": "钉钉用户",
                "hidden": false,
                "disable": false,
                "index": "/main/table?featureCode=dd_user_code"
            },
            {
                "children": [],
                "icon": "",
                "title": "提测申请记录",
                "hidden": false,
                "disable": false,
                "index": "/main/table?featureCode=ApproveTestApply"
            }
        ],
        "icon": "el-icon-menu",
        "title": "业务模块",
        "hidden": false,
        "disable": false,
        "index": "business"
    },
    {
        "index": "/main",
        "title": "平台维护",
        "icon": "el-icon-s-tools",
        "hidden": false,
        "disable": false,
        "children": [
            {
                "index": "/main/meta-data",
                "title": "元数据管理",
                "icon": "el-icon-warning",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/feature",
                "title": "功能维护",
                "icon": "el-icon-warning-outline",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/global-conf-list",
                "title": "组件全局配置",
                "icon": "el-icon-star-off",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/instance-conf-list",
                "title": "组件实例配置",
                "icon": "el-icon-star-on",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/form-builder",
                "title": "表单构建",
                "icon": "el-icon-s-order",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/meta-conf",
                "title": "MetaConf",
                "icon": "el-icon-s-tools",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/meta-dict",
                "title": "字典",
                "icon": "el-icon-collection",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/meta-exception",
                "title": "异常",
                "icon": "el-icon-warning",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/meta-version",
                "title": "DbVersion",
                "icon": "el-icon-paperclip",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/objects",
                "title": "动态元对象",
                "icon": "el-icon-menu",
                "hidden": false,
                "disable": false
            },
            {
                "index": "/main/demo",
                "title": "组件概览",
                "icon": "el-icon-menu",
                "hidden": false,
                "disable": false
            }
        ]
    }
]

export default [
    {
        url: '/menu/tree',
        type: 'get',
        disable: false,
        response: config => {
            console.log('mock: /menu/tree');
            return {
                code: 200,
                state: 'ok',
                data: menus
            }
        }
    }
]