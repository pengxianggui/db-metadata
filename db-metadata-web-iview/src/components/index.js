import BoolBox from './core/boolbox'
import CheckBox from './core/checkbox'
import DataList from './core/datalist'
import DateBox from './core/datebox'
import DateTimeBox from './core/datetimebox'
import DialogBox from './core/dialogbox'
import DropDownBox from './core/dropdownbox'
import FileBox from './core/filebox'
import FindBox from './core/findbox'
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
    // locale.use(opts.locale);
    // locale.i18n(opts.i18n);

    components.forEach(component => {
        Vue.component(component.name, component);
    });

    // Vue.use(Loading.directive);
    // 全局方法
    Vue.use(RegisterGlobalFn);
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