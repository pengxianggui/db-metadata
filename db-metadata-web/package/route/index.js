import MetaDataManager from "../meta/MetaDataManager";
import FormBuilder from '../meta/form-builder'
import GlobalConfList from "../meta/component/GlobalConfList";
import GlobalConf from "../meta/component/GlobalConf";
import InstanceConfList from "../meta/component-instance/InstanceConfList";
import InstanceConfEdit from "../meta/component-instance/InstanceConfEdit";
import InstanceConfNew from "../meta/component-instance/InstanceConfNew";
import MetaFeatureList from '../meta/feature';
import MetaConfList from "../meta/meta-conf";
import DictList from "../meta/dict"
import ExceptionList from '../meta/exception'

import {access} from '../constant/variable'

const jumpOut = [
    {
        path: 'instance-conf-edit',
        component: InstanceConfEdit,
        name: 'InstanceConfEdit',
        meta: {
            title: '组件实例配置-编辑',
            icon: 'edit',
            noCache: false,
            roles: [access.adminRoleCode]
        },
        hidden: true
    }
];

export default [
    {
        path: 'meta-data',
        name: 'Metadata',
        meta: {
            title: "元数据管理",
            icon: "el-icon-warning",
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: MetaDataManager
    }, {
        path: 'feature',
        name: 'Feature',
        meta: {
            title: '功能维护',
            icon: 'el-icon-warning-outline',
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: MetaFeatureList
    }, {
        path: 'global-conf-list',
        name: 'MetaComponent',
        meta: {
            title: "组件全局配置",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: GlobalConfList
    }, {
        path: 'global-conf',
        name: 'MetaComponentEdit',
        meta: {
            title: "组件全局配置-编辑",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.adminRoleCode]
        },
        hidden: true,
        component: GlobalConf
    }, {
        path: 'instance-conf-list',
        name: 'MetaComponentInstance',
        meta: {
            title: "组件实例配置",
            icon: "el-icon-star-on",
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: InstanceConfList
    }, {
        path: 'instance-conf-new',
        name: 'InstanceConfNew',
        meta: {
            title: "组件实例配置-新增",
            icon: "el-icon-star-off",
            noCache: false,
            roles: [access.adminRoleCode]
        },
        hidden: true,
        component: InstanceConfNew
    }, {
        path: 'form-builder',
        name: 'FormBuilder',
        meta: {
            title: "表单构建",
            icon: "el-icon-s-order",
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: FormBuilder
    }, {
        path: 'meta-conf',
        name: 'MetaConf',
        meta: {
            title: 'MetaConf',
            icon: 'el-icon-s-tools',
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: MetaConfList
    }, {
        path: 'meta-dict',
        name: 'MetaDict',
        meta: {
            title: '字典',
            icon: 'el-icon-collection',
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: DictList
    }, {
        path: 'meta-exception',
        name: 'MetaException',
        meta: {
            title: '异常',
            icon: 'el-icon-warning',
            noCache: false,
            roles: [access.adminRoleCode]
        },
        component: ExceptionList
    },
    ...jumpOut
]
