import utils from './utils'
import * as Rest from './utils/rest'
import configUrl from './constant/url'
import configFilters from './register/filter'
import configDirectives from './register/directive'
import registerGlobalFunction from './register/global-function'
import './asserts/svg/index' // 内置svg注册
// 布局组件
import Layout from "./layout";
import {MetaHeader, MetaLayout} from './layout'
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
import SearchView from './view/searchview'
import SqlBox from './core/sqlbox'
import Tags from './core/tags'
import TableView from './view/tableview'
import TableTreeView from './view/tabletreeview'
import TextAreaBox from './core/textareabox'
import TextBox from './core/textbox'
import TimeBox from './core/timebox'
import TreeView from './core/treeview'
import ZTogglePanel from './core/ztogglepanel'
import SvgIcon from "./core/svgicon"
import FullScreen from "./core/fullscreen/src/FullScreen"
import PageSelector from "./core/pageselector/src/PageSelector";
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
import User from './access'
// 内置业务组件
import {UserList, RoleList} from '@/../package/meta/rbac'
// 全局页面
import GlobalPage from '@/../package/page'

import Route from "./route";
import Menu from "./menu";
import {configApp} from "./config";
import Theme from './theme'

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
    PageSelector,

    // 布局组件layout
    Layout,

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
    RoleList,
    // 全局页面
    GlobalPage
];

const install = function (Vue, opts = {}) {
    if (install.installed) return;

    // 自定义url(接口url和路由url)覆盖: 优先级最高
    configUrl(opts)

    // 注册全局函数: 最优先。会配置axios，这是后面都可能需要的
    registerGlobalFunction(Vue, opts)
    // 注册全局过滤器
    configFilters(Vue)
    // 注册全局自定义指令
    configDirectives(Vue)

    // 全局组件注册
    components.map(component => {
        if (component.install) {
            Vue.use(component, opts)
        } else {
            Vue.component(component.name, component)
        }
    })

    // 系统配置: 获取服务端关于系统设置的数据
    configApp(Vue, opts).then(() => {
        // 主题配置: 配置默认主题
        Theme.configDefaultTheme(opts)

        // 静态角色配置
        if (opts.access) {
            utils.reverseMerge(access, opts.access, false);
        }

        // 注册菜单
        Menu.registerMenu(Vue, opts)
        // 注册路由： 必须在全局组件注册滞后，因为路由数据中的component需要转换为全局组件
        Route.registerRouter(Vue, opts)
    }).catch(err => {
        console.error(err)
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
    User,

    MetaHeader,
    MetaLayout
}
