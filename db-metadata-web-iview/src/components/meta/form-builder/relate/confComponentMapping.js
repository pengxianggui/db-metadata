import {DEFAULT, URL} from "@/constant";
import utils from '@/utils'

/**
 * 自定义哪些属性配置使用什么控件
 * @type {{lineNumbers: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), editable: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), component_name: *, delete_url: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), conf: (DEFAULT_CONF.JsonBox|{inline, component_name, name, default_value, conf, label}), "label-position": *, label: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), check: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), mode: *, expand: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), multi_select: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), inline: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), name: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), options: {component_name: string}, action: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), theme: *, data_url: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), group: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), check_url: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label})}}
 */
const CUSTOM_CONF_COMPONENT_MAPPING = {
    name: DEFAULT.TextBox,
    label: DEFAULT.TextBox,
    component_name: utils.merge({data_url: URL.COMPONENT_CODE_LIST}, DEFAULT.DropDownBox),
    conf: DEFAULT.JsonBox,
    inline: DEFAULT.BoolBox,
    data_url: DEFAULT.TextBox,
    delete_url: DEFAULT.TextBox,
    multi_select: DEFAULT.BoolBox,
    editable: DEFAULT.BoolBox,
    options: {
        component_name: 'OptionsInput'
    },
    group: DEFAULT.BoolBox,
    expand: DEFAULT.BoolBox,
    "label-position": utils.merge({
        options: ['top-center', 'top-left', 'top-right', 'bottom-center', 'bottom-left', 'bottom-right']
    }, DEFAULT.DropDownBox),
    action: DEFAULT.TextBox,
    check: DEFAULT.BoolBox,
    check_url: DEFAULT.TextBox,
    theme: utils.merge({options: ['default', 'ambiance']}, DEFAULT.RadioBox),
    lineNumbers: DEFAULT.BoolBox,
    mode: utils.merge({options: ['text/x-mysql']}, DEFAULT.DropDownBox)
};

export default CUSTOM_CONF_COMPONENT_MAPPING;