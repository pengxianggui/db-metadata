import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import utils from './utils'
import * as Rest from './utils/rest'
import * as user from './utils/user'
import axios from './axios'
import filters from './register/filter'
// 核心组件
import BoolBox from './core/boolbox'
import CheckBox from './core/checkbox'
import CodeBox from './core/codebox'
import DataList from './core/datalist'
import DateBox from './core/datebox'
import DateTimeBox from './core/datetimebox'
import DialogBox from './core/dialogbox'
import DropDownBox from './core/dropdownbox'
import FileBox from './core/filebox'
import FindBox from './core/findbox'
import FormView from './core/form'
import RowGrid from './core/grid'
import IconBox from './core/iconbox'
import ImgBox from './core/imgbox'
import JsonBox from './core/jsonbox'
import List from './core/list'
import ListItem from './core/listitem'
import MiniFormBox from './core/miniformbox'
import NumBox from './core/numbox'
import PassBox from './core/passbox'
import PopMenu from './core/popmenu'
import RadioBox from './core/radiobox'
import RichTextBox from "./core/richtextbox";
import SearchPanel from './core/searchpanel'
import SqlBox from './core/sqlbox'
import Tags from './core/tags'
import TableList from './core/tablelist'
import TableTreeList from './core/tabletreelist'
import TextAreaBox from './core/textareabox'
import TextBox from './core/textbox'
import TimeBox from './core/timebox'
import Tree from './core/tree'
import ZTogglePanel from './core/ztogglepanel'
// 模板组件
import DataListTableTmpl from './template/DataListTableTmpl'
import FormTmpl from './template/FormTmpl'
import MasterSlaveTableTmpl from './template/MasterSlaveTableTmpl'
import SingleGridTmpl from './template/SingleGridTmpl'
import TableFormTmpl from './template/TableFormTmpl'
import TreeFormTmpl from './template/TreeFormTmpl'
import TreeTableTmpl from './template/TreeTableTmpl'
import TreeSingleGridTmpl from './template/TreeSingleGridTmpl'
// meta 组件
import {MetaEasyEdit, MiniFormField, MiniFormObject} from "./core/meta"
import MetaDataManager from "./meta/MetaDataManager";
import FormBuilder from './meta/form-builder'
import GlobalConfList from "./meta/component/GlobalConfList";
import GlobalConf from "./meta/component/GlobalConf";
import InstanceConfList from "./meta/component-instance/InstanceConfList";
import InstanceConfEdit from "./meta/component-instance/InstanceConfEdit";
import InstanceConfNew from "./meta/component-instance/InstanceConfNew";
import MetaFeatureList from './meta/feature';
import MetaConfList from "./meta/meta-conf";
import DictList from "./meta/dict"
import ExceptionList from './meta/exception'
import {restUrl, routeUrl} from './constant/url'
import {access, innerFeatureCode, innerObjectCode} from "./constant/variable";
// 内置路由
import MetaRoute from './route'
import MetaMenu from './meta/meta-menu'
// style
import './style/index.scss'

// autoLoadingGlobalComponent()

const components = [
    // atom or container
    BoolBox,
    CheckBox,
    CodeBox,
    DataList,
    DateBox,
    DateTimeBox,
    DialogBox,
    DropDownBox,
    FileBox,
    FindBox,
    FormView,
    RowGrid,
    IconBox,
    ImgBox,
    JsonBox,
    List,
    ListItem,
    MiniFormBox,
    MiniFormObject,
    MiniFormField,
    MetaEasyEdit,
    NumBox,
    PassBox,
    PopMenu,
    RadioBox,
    RichTextBox,
    SearchPanel,
    SqlBox,
    Tags,
    TableList,
    Tree,
    TableTreeList,
    TextAreaBox,
    TextBox,
    TimeBox,
    ZTogglePanel,

    // template
    DataListTableTmpl,
    FormTmpl,
    MasterSlaveTableTmpl,
    SingleGridTmpl,
    TableFormTmpl,
    TreeFormTmpl,
    TreeTableTmpl,

    // meta
    MetaDataManager,
    FormBuilder,
    GlobalConfList,
    GlobalConf,
    InstanceConfList,
    InstanceConfEdit,
    InstanceConfNew,
    MetaFeatureList,
    MetaConfList,
    DictList,
    ExceptionList,

    // 内置meta菜单组件
    MetaMenu,
];

const install = function (Vue, opts = {}) {
    if (install.installed) return;

    Vue.use(ElementUI, opts);
    // 自定义路由url覆盖
    if (opts.routeUrl) {
        utils.reverseMerge(routeUrl, opts.routeUrl, false);
    }
    // 自定义rest接口url覆盖
    if (opts.restUrl) {
        utils.reverseMerge(restUrl, opts.restUrl, false);
    }

    // 自定义元对象编码覆盖: 用于meta 路由菜单
    if (opts.objectCode) {
        utils.reverseMerge(innerObjectCode, opts.objectCode, false);
    }
    // 自定义功能编码覆盖: 用于meta 路由菜单
    if (opts.featureCode) {
        utils.reverseMerge(innerFeatureCode, opts.featureCode, false);
    }
    // 角色配置
    if (opts.access) {
        if (opts.access.hasOwnProperty('root')) {
            Vue.prototype.$root = access.root; // 全局注入组件
        }
        utils.reverseMerge(access, opts.access, false);
    }

    // 注册全局函数
    Vue.prototype.$axios = axios(opts['axios']);    // {axios: {}} // 对axios进行配置, 如baseURL等
    Vue.prototype.$merge = utils.merge;
    Vue.prototype.$reverseMerge = utils.reverseMerge;
    Vue.prototype.$compile = utils.compile;
    Vue.prototype.$dialog = utils.dialog;
    Vue.prototype.$isRoot = utils.isRoot;

    // 注册全局过滤器
    Object.keys(filters).map(key => Vue.filter(key, filters[key]))

    components.map(component => Vue.component(component.name, component))
};

if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue);
}

export default {
    install,
    utils,
    Rest,
    user,
    routeUrl,
    restUrl,

    // atom or container
    BoolBox,
    CheckBox,
    CodeBox,
    DataList,
    DateBox,
    DateTimeBox,
    DialogBox,
    DropDownBox,
    FileBox,
    FindBox,
    FormView,
    RowGrid,
    IconBox,
    ImgBox,
    JsonBox,
    List,
    ListItem,
    MiniFormBox,
    MiniFormObject,
    MiniFormField,
    MetaEasyEdit,
    NumBox,
    PassBox,
    PopMenu,
    RadioBox,
    RichTextBox,
    SearchPanel,
    SqlBox,
    Tags,
    TableList,
    Tree,
    TableTreeList,
    TextAreaBox,
    TextBox,
    TimeBox,
    ZTogglePanel,

    // template
    DataListTableTmpl,
    FormTmpl,
    MasterSlaveTableTmpl,
    SingleGridTmpl,
    TableFormTmpl,
    TreeFormTmpl,
    TreeTableTmpl,
    TreeSingleGridTmpl,

    // meta
    MetaDataManager,
    FormBuilder,
    GlobalConfList,
    GlobalConf,
    InstanceConfList,
    InstanceConfEdit,
    InstanceConfNew,
    MetaFeatureList,
    MetaConfList,
    DictList,
    ExceptionList,

    // 内置meta菜单
    MetaMenu
}

export {
    utils,
    Rest,
    routeUrl,
    restUrl,
    user,

    // atom or container
    BoolBox,
    CheckBox,
    CodeBox,
    DataList,
    DateBox,
    DateTimeBox,
    DialogBox,
    DropDownBox,
    FileBox,
    FindBox,
    FormView,
    RowGrid,
    IconBox,
    ImgBox,
    JsonBox,
    List,
    ListItem,
    MiniFormBox,
    MiniFormObject,
    MiniFormField,
    MetaEasyEdit,
    NumBox,
    PassBox,
    PopMenu,
    RadioBox,
    RichTextBox,
    SearchPanel,
    SqlBox,
    Tags,
    TableList,
    Tree,
    TableTreeList,
    TextAreaBox,
    TextBox,
    TimeBox,
    ZTogglePanel,

    // template
    DataListTableTmpl,
    FormTmpl,
    MasterSlaveTableTmpl,
    SingleGridTmpl,
    TableFormTmpl,
    TreeFormTmpl,
    TreeTableTmpl,
    TreeSingleGridTmpl,

    // meta
    MetaDataManager,
    FormBuilder,
    GlobalConfList,
    GlobalConf,
    InstanceConfList,
    InstanceConfEdit,
    InstanceConfNew,
    MetaFeatureList,
    MetaConfList,
    DictList,
    ExceptionList,

    // 内置路由
    MetaRoute,
    MetaMenu
}
