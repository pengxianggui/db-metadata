// 基础组件
import TextBox from "../../../core/textbox/src/TextBox";
import TextAreaBox from "../../../core/textareabox/src/TextAreaBox";
import PassBox from "../../../core/passbox/src/PassBox";
import NumBox from "../../../core/numbox/src/NumBox";
import BoolBox from "../../../core/boolbox/src/BoolBox";
import CheckBox from "../../../core/checkbox/src/CheckBox";
import RadioBox from "../../../core/radiobox/src/RadioBox";
import DropDownBox from "../../../core/dropdownbox/src/DropDownBox";
import DateBox from "../../../core/datebox/src/DateBox";
import TimeBox from "../../../core/timebox/src/TimeBox";
import DateTimeBox from "../../../core/datetimebox/src/DateTimeBox";
import FileBox from "../../../core/filebox/src/FileBox";
import ImgBox from "../../../core/imgbox/src/ImgBox";
// 高级组件
import FindBox from "../../../core/findbox/src/FindBox";
import JsonBox from "../../../core/jsonbox/src/JsonBox";
import IconBox from "../../../core/iconbox/src/IconBox";
import RegionBox from "../../../core/regionbox/src/RegionBox";
import RichTextBox from "../../../core/richtextbox/src/RichTextBox";
import RowGrid from "../../../core/rowgrid/src/RowGrid";

const compLib = {
    "布局组件": [{
        icon: 'rowgrid',
        comp: RowGrid
    }],
    "基础组件": [
        {
            icon: 'textbox',
            comp: TextBox
        },
        {
            icon: 'textareabox',
            comp: TextAreaBox
        },
        {
            icon: 'passbox',
            comp: PassBox
        },
        {
            icon: 'numbox',
            comp: NumBox
        },
        {
            icon: 'boolbox',
            comp: BoolBox
        },
        {
            icon: 'checkbox',
            comp: CheckBox
        },
        {
            icon: 'radiobox',
            comp: RadioBox
        },
        {
            icon: 'dropdownbox',
            comp: DropDownBox
        },
        {
            icon: 'datebox',
            comp: DateBox
        },
        {
            icon: 'timebox',
            comp: TimeBox
        },
        {
            icon: 'datetimebox',
            comp: DateTimeBox
        },
        {
            icon: 'filebox',
            comp: FileBox
        },
        {
            icon: 'imgbox',
            comp: ImgBox
        }
    ],
    "高级组件": [
        {
            icon: 'findbox',
            comp: FindBox
        },
        {
            icon: 'jsonbox',
            comp: JsonBox
        },
        {
            icon: 'iconbox',
            comp: IconBox
        },
        {
            icon: 'regionbox',
            comp: RegionBox
        },
        {
            icon: 'richtextbox',
            comp: RichTextBox
        }
    ]
}

/**
 * 根据组件名判断是否容器组件
 * @param componentName
 * @returns {boolean}
 */
export const isLayoutComp = (componentName) => {
    return extract(compLib['布局组件']).findIndex(c => c.name === componentName) > -1
}

// 拍平: 提取组件列表
export const extract = (value) => {
    return value.map(e => e.comp)
}

export const compList = Object.keys(compLib).map(k => extract(compLib[k]))
export default compLib
