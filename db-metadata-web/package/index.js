import utils from './utils'
import * as Rest from './utils/rest'

// 布局组件
import Layout from "./layout";
import {MetaHeader, MetaLayout} from './layout'

// 基础组件
import BoolBox from './core/boolbox'
import CheckBox from './core/checkbox'
import CodeBox from './core/codebox'
import List from "./core/list";
import ListItem from "./core/listitem";
import DateBox from './core/datebox'
import DateTimeBox from './core/datetimebox'
import DialogBox from './core/dialogbox'
import DropDownBox from './core/dropdownbox'
import FindBox from './core/findbox'
import RowGrid from './core/rowgrid'
import IconBox from './core/iconbox'
import Uploadbox from "./core/uploadbox";
import ImgBox from './core/imgbox'
import FileBox from './core/filebox'
import JsonBox from './core/jsonbox'
import MiniFormBox from './core/miniformbox'
import NumBox from './core/numbox'
import PassBox from './core/passbox'
import PopMenu from './core/popmenu'
import RadioBox from './core/radiobox'
import RichTextBox from "./core/richtextbox";
import RegionBox from "./core/regionbox";
import SearchView from './view/searchview'
import Tags from './core/tags'
import TextAreaBox from './core/textareabox'
import TextBox from './core/textbox'
import TimeBox from './core/timebox'
import ZTogglePanel from './core/ztogglepanel'
import SvgIcon from "./core/svgicon"
import FullScreen from "./core/fullscreen/src/FullScreen"
import PageSelector from "./core/pageselector/src/PageSelector";

// 容器组件
import TableView from './view/tableview'
import TableTreeView from './view/tabletreeview'
import TreeView from './view/treeview'
import ListView from './view/listview'
import FormView from './view/formview'

// 模板组件
import FormTmpl from './template/FormTmpl'
import MasterSlaveTableTmpl from './template/MasterSlaveTableTmpl'
import SingleGridTmpl from './template/SingleGridTmpl'
import TableFormTmpl from './template/TableFormTmpl'
import TreeFormTmpl from './template/TreeFormTmpl'
import TreeTableTmpl from './template/TreeTableTmpl'
import TreeSingleGridTmpl from './template/TreeSingleGridTmpl'

// meta 组件
import {MetaEasyEdit, MiniFormField, MiniFormObject} from "./core/meta"

// 系统
import './asserts/svg/index' // 内置svg注册
import {restUrl, routeUrl} from './constant/url'
import {access} from "./access";
import Token from "./token"
import User from './access'
import Route from "./route"
import Menu from "./menu"
import {configApp} from "./config"
import Theme from './theme'

import configUrl from './constant/url'
import configFilters from './register/filter'
import configDirectives from './register/directive'
import registerGlobalFunction from './register/global-function'

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
    FindBox,
    FormView,
    RowGrid,
    IconBox,
    Uploadbox,
    ImgBox,
    FileBox,
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
    FormTmpl,
    MasterSlaveTableTmpl,
    SingleGridTmpl,
    TableFormTmpl,
    TreeFormTmpl,
    TreeTableTmpl,
    TreeSingleGridTmpl
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

    // 系统配置: 获取服务端关于系统设置的数据. 注意:
    // 这里不能采用"内部返回Promise回调，在回调中执行后续配置"的原因是, 会导致VueRouter的挂载优先执行，从而产生一个bug: 在动态路由下刷新，
    // 未能触发db-metadata内部的路由钩子，从而导致路由钩子内的用户检测失效，直接表现用户数据丢失，虽然token还在。
    // 采用同步阻塞是必要的，因为configApp获取的系统配置信息，里面有一些内容必须是先获取到，如果await/async不能满足，必要时可以采用原生XHR
    // 进行同步请求访问。
    // 2022-5-1: await/async依然不能满足需求，因为await/async机制必须满足一个条件，整个调用链都必须用这两个关键词去修饰。但是由于业务系统中
    //  采用的是Vue.use, 而在Vue.use前面加await是不行的，编译就通过不了，这就导致代码的执行顺序是configApp执行后， 调用链一直向上，凡是加了await的
    //  地方后面就等待，因此主题配置、菜单、路由注册是等待了，但是Vue.use没法加await，所以Vue.use后面的代码就不等了，因此new Vue还是先执行，
    //  也就是router先挂载了。而configApp后面的代码: 主题配置、菜单、路由数据、路由拦截器的执行却实实在在被阻塞了，结果就是router先注册，
    //  registerInterceptor里的beforeEach拦截器后注册。因此，产生bug: 编程式的路由，如果直接刷新，那么beforeEach钩子未执行，导致用户状态丢失。
    //
    //  因此总结一下， 必须实现:
    //  1. configApp必须返回后端数据后，再执行后面的配置(因为后面的配置依赖后端响应的系统配置数据)
    //  2. router.beforeEach又必须先于new Vue({router})执行
    //  因此configApp必须是同步的，且同步效果要一直传递到外面的Vue.use, 因此await/async不支持。采取的办法是configApp内部采用原生的XMLHttpRequest。
    //  这种方式也存在一个问题: 那就是同步导致的浏览器阻塞，如果这个请求费了10s, 浏览器就会卡10s, 但是这是可以讲得清楚的。因为系统配置是系统访问前必须加载的。
    configApp(Vue, opts)

    // 主题配置: 配置默认主题
    Theme.configDefaultTheme(opts)

    // 静态角色配置
    if (opts.access) {
        utils.reverseMerge(access, opts.access, false);
    }

    Menu.registerMenu(Vue, opts) // 注册菜单
    Route.registerRouteData(Vue, opts) // 注册路由
    Route.registerInterceptor(Vue, opts) // 路由拦截器
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
    Token,
    MetaHeader,
    MetaLayout
}
