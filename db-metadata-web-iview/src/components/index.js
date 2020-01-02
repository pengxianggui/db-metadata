import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// 功能组件
import BoolBox from './core/boolbox'
import CheckBox from './core/checkbox'
import DataList from './core/datalist'
import DateBox from './core/datebox'
import DateTimeBox from './core/datetimebox'
import DialogBox from './core/dialogbox'
import DropDownBox from './core/dropdownbox'
import FileBox from './core/filebox'
import FindBox from './core/findbox'
import FindPanel from './core/findpanel'
import FormTmpl from './core/form'
import RowGrid from './core/grid'
import ImgBox from './core/imgbox'
import JsonBox from './core/jsonbox'
import List from './core/list'
import ListItem from './core/listitem'
import MiniFormBox from './core/miniformbox'
import NumBox from './core/numbox'
import PassBox from './core/passbox'
import PopMenu from './core/popmenu'
import RadioBox from './core/radiobox'
import SearchPanel from './core/searchpanel'
import SqlBox from './core/sqlbox'
import TableList from './core/tablelist'
import TableTreeList from './core/tabletreelist'
import TextAreaBox from './core/textareabox'
import TextBox from './core/textbox'
import TimeBox from './core/timebox'
import ZTogglePanel from './core/ztogglepanel'

// 模板组件
// import DataListTableTmpl from './template/DataListTableTmpl'

import RegisterGlobalFn from '@/config/auto-register-fn'

const components = [
    BoolBox,
    CheckBox,
    DataList,
    DateBox,
    DateTimeBox,
    DialogBox,
    DropDownBox,
    FileBox,
    FindBox,
    FindPanel,
    FormTmpl,
    RowGrid,
    ImgBox,
    JsonBox,
    List,
    ListItem,
    MiniFormBox,
    NumBox,
    PassBox,
    PopMenu,
    RadioBox,
    SearchPanel,
    SqlBox,
    TableList,
    TableTreeList,
    TextAreaBox,
    TextBox,
    TimeBox,
    ZTogglePanel
];

const install = function (Vue, opts = {}) {
    Vue.use(ElementUI, opts);
    // 全局方法
    Vue.use(RegisterGlobalFn, opts);

    components.forEach(component => {
        Vue.component(component.name, component);
    });
};

if (typeof window !== 'undefined' && window.Vue) {
    install(window.Vue);
}

export default {
    install,
    BoolBox,
    CheckBox,
    DataList,
    DateBox,
    DateTimeBox,
    DialogBox,
    DropDownBox,
    FileBox,
    FindBox,
    FindPanel,
    FormTmpl,
    RowGrid,
    ImgBox,
    JsonBox,
    List,
    ListItem,
    MiniFormBox,
    NumBox,
    PassBox,
    PopMenu,
    RadioBox,
    SearchPanel,
    SqlBox,
    TableList,
    TableTreeList,
    TextAreaBox,
    TextBox,
    TimeBox,
    ZTogglePanel
}