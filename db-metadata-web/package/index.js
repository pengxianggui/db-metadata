import utils from './utils'
import * as Rest from './utils/rest'
import filters from './register/filter'
import directives from "./register/directive";
import registerGlobalFunction from './register/global-function'
import './svg/index' // 内置svg注册
// 布局组件
import NavMenu from "./core/navmenu";
import TagView from "./core/tagview";
import MetaLayout from "./layout/MetaLayout";
import MetaHeader from "./layout/MetaHeader";
import MetaMain from './layout/admin/index'
// 基础组件
import BoolBox from './core/boolbox'
import CheckBox from './core/checkbox'
import CodeBox from './core/codebox'
import List from "./core/list";
import ListItem from "./core/listitem";
import ListView from './core/listview'
import DateBox from './core/datebox'
import DateTimeBox from './core/datetimebox'
import DialogBox from './core/dialogbox'
import DropDownBox from './core/dropdownbox'
import FileBox from './core/filebox'
import FindBox from './core/findbox'
import FormView from './core/formview'
import RowGrid from './core/rowgrid'
import IconBox from './core/iconbox'
import ImgBox from './core/imgbox'
import JsonBox from './core/jsonbox'
import MiniFormBox from './core/miniformbox'
import NumBox from './core/numbox'
import PassBox from './core/passbox'
import PopMenu from './core/popmenu'
import RadioBox from './core/radiobox'
import RichTextBox from "./core/richtextbox";
import RegionBox from "./core/regionbox";
import SearchView from './core/searchview'
import SqlBox from './core/sqlbox'
import Tags from './core/tags'
import TableView from './core/tableview'
import TableTreeView from './core/tabletreeview'
import TextAreaBox from './core/textareabox'
import TextBox from './core/textbox'
import TimeBox from './core/timebox'
import TreeView from './core/treeview'
import ZTogglePanel from './core/ztogglepanel'
import SvgIcon from "./core/svgicon"
import FullScreen from "./core/fullscreen/src/FullScreen"
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
import {restUrl, routeUrl} from './constant/url'
import {access} from "./access";
import user from './access'
// 内置业务组件
import {UserList, RoleList} from '@/../package/meta/rbac'

// 内置菜单: Meta维护菜单
import MetaMenu from "./menu/MetaMenu";

import Route from "./route";
import Menu from "./menu";
import {configApp} from "./config";

// 暴露tagView操作的接口
import TagViewUtil from '@/../package/core/tagview/data'

// style
import 'element-ui/lib/theme-chalk/index.css' // element
import './style/index.scss'

// 全局注册的组件
const components = [
    // atom or container
    BoolBox,
    CheckBox,
    CodeBox,
    List,
    ListItem,
    ListView,
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
    MiniFormBox,
    MiniFormObject,
    MiniFormField,
    MetaEasyEdit,
    NumBox,
    PassBox,
    PopMenu,
    RadioBox,
    RichTextBox,
    RegionBox,
    SearchView,
    SqlBox,
    Tags,
    TableView,
    TreeView,
    TableTreeView,
    TextAreaBox,
    TextBox,
    TimeBox,
    ZTogglePanel,
    SvgIcon,
    FullScreen,

    // 布局组件layout
    MetaLayout,
    MetaHeader,
    MetaMain,
    NavMenu,
    TagView,

    // template
    DataListTableTmpl,
    FormTmpl,
    MasterSlaveTableTmpl,
    SingleGridTmpl,
    TableFormTmpl,
    TreeFormTmpl,
    TreeTableTmpl,
    TreeSingleGridTmpl,

    // 内置业务组件
    UserList,
    RoleList
];

const install = function (Vue, opts = {}) {
    if (install.installed) return;

    // 自定义rest接口url覆盖: 优先级最高
    if (opts.restUrl) {
        utils.reverseMerge(restUrl, opts.restUrl, false);
    }

    // 系统配置: 最优先
    configApp(Vue, opts)

    // 注册全局函数
    registerGlobalFunction(Vue, opts)

    // 注册全局过滤器
    Object.keys(filters).map(key => Vue.filter(key, filters[key]))
    // 注册全局自定义指令
    Object.keys(directives).map(key => Vue.directive(key, directives[key]))

    // 静态角色配置
    if (opts.access) {
        utils.reverseMerge(access, opts.access, false);
    }

    // 注册菜单
    Menu.registerMenu(Vue, opts)
    // 注册路由
    Route.registerRouter(Vue, opts)

    components.map(component => {
        if (component.install) {
            Vue.use(component, opts)
        } else {
            Vue.component(component.name, component)
        }
    })
};

if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue);
}

export default {
    install
}

export {
    utils,
    Rest,
    routeUrl,
    restUrl,
    user,
    MetaMenu,

    TagViewUtil,
    MetaLayout,
    MetaHeader,
    MetaMain
}
