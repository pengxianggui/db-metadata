import {URL} from '@/constant'
import commonRoutes from '@/router/commonRoute'

import axios from "axios";
import MockAdapter from 'axios-mock-adapter'

let mockAxios = new MockAdapter(axios);

const mockMenus = [
    {
        path: 'business',
        icon: 'el-icon-s-custom',
        label: '业务模块',
        children: [{
            path: '/main/table?objectCode=test_table',
            icon: 'el-icon-s-help',
            label: '单表CRUD'
        }, {
            path: '/main/ms-table?featureCode=meta_manager',
            icon: 'el-icon-s-help',
            label: '主子表'
        }, {
            path: '/main/demo',
            icon: 'el-icon-menu',
            label: '组件概览'
        }, {
            path: '/main/objects',
            icon: 'el-icon-menu',
            label: '动态元对象'
        }]
    }, {
        path: 'system',
        icon: 'el-icon-s-grid',
        label: '系统管理',
        children: [{
            path: '/main/table?objectCode=change_log',
            icon: 'el-icon-finished',
            label: 'ChangeLog'
        }, {
            path: '/main/table?featureCode=DbVersionSingleGrid',
            icon: 'el-icon-paperclip',
            label: 'DbVersion'
        }]
    }, {
        path: 'meta',
        icon: 'el-icon-s-tools',
        label: '平台维护',
        children: [{
            path: '/main/table?objectCode=meta_feature',
            icon: 'el-icon-warning-outline',
            label: '功能维护'
        }, {
            path: '/main/meta-data',
            icon: 'el-icon-warning',
            label: '元数据管理'
        }, {
            path: '/main/meta-component',
            icon: 'el-icon-star-off',
            label: '组件全局配置'
        }, {
            path: '/main/meta-component-instance',
            icon: 'el-icon-star-on',
            label: '组件实例配置'
        }, {
            path: '/main/form-builder',
            icon: 'el-icon-s-order',
            label: '表单构建'
        }, {
            path: '/main/table?objectCode=meta_config',
            icon: 'el-icon-s-tools',
            label: 'MetaConfig'
        }, {
            path: '/main/table?objectCode=meta_dict',
            icon: 'el-icon-collection',
            label: '字典'
        }]
    },
];

mockAxios.onGet(URL.ROUTE_DATA).reply(200, {
    data: commonRoutes,
    state: 'ok'
}).onGet(URL.MENU_DATA).reply(200, {
    data: mockMenus,
    state: 'ok'
})
    .onAny().passThrough();