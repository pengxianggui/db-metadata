const menus = [
    // {
    //     "children": [
    //         {
    //             "children": [{
    //                 "index": "/main/test",
    //                 "title": "TEST",
    //                 "icon": "el-icon-edit"
    //             }],
    //             "icon": "el-icon-edit",
    //             "title": "元数据管理",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/ms-table?featureCode=meta_manager"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-s-custom",
    //             "title": "钉钉用户",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/ding-user?featureCode=dd_user_menu"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-menu",
    //             "title": "鹊桥用户",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/magpie-user?featureCode=mb_user_menu"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-s-custom",
    //             "title": "Gitlab用户",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/gitlab-user?featureCode=gitlab_user"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-s-custom",
    //             "title": "Jira用户",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/jira-user?featureCode=jira_user_menu"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-s-help",
    //             "title": "Jira项目",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/jira-project?featureCode=jira_project_menu"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-s-goods",
    //             "title": "Gitlab项目",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/table?featureCode=gitlab_project_menu"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-menu",
    //             "title": "测试表",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/table?featureCode=test_table_menu"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-message-solid",
    //             "title": "v_one_view_11",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/table?featureCode=v_one_view_11"
    //         },
    //         {
    //             "children": [],
    //             "icon": "el-icon-s-data",
    //             "title": "钉钉用户",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/table?featureCode=dd_user_code"
    //         },
    //         {
    //             "children": [],
    //             "icon": "",
    //             "title": "提测申请记录",
    //             "hidden": false,
    //             "disable": false,
    //             "index": "/main/table?featureCode=ApproveTestApply"
    //         }
    //     ],
    //     "icon": "el-icon-menu",
    //     "title": "业务模块",
    //     "hidden": false,
    //     "disable": false,
    //     "index": "business"
    // }
]

export default [
    {
        url: '/menu',
        type: 'get',
        disable: true,
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