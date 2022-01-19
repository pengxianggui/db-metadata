import IconBox from './src/IconBox'
import IconPanel from "./src/IconPanel";

IconBox.install = function (Vue) {
    Vue.component(IconBox.name, IconBox);
    Vue.component(IconPanel.name, IconPanel);
};

export default IconBox;

/**
 * IconBox必须和SvgIcon配合使用， 即IconBox选中的图标内容, 必须由SvgIcon去应用
 * 自扩展的的svg图标, 必须以symbol的方式应用, 且symbol id的格式必须是: #me-icon-[name]
 */
