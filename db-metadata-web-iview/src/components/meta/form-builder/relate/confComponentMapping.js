import {URL} from "@/constant";
import utils from '@/utils'
import DefaultBoolBoxMeta from '@/components/core/boolbox/ui-conf'
import DefaultRadioBoxMeta from '@/components/core/radiobox/ui-conf'
import DefaultTextBoxMeta from '@/components/core/textbox/ui-conf'
import DefaultDropDownBoxMeta from '@/components/core/dropdownbox/ui-conf'
import DefaultMinFormBoxMeta from '@/components/core/miniformbox/ui-conf'

/**
 * 自定义哪些属性配置使用什么控件
 * @type {{lineNumbers: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), editable: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), component_name: *, delete_url: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), conf: (DEFAULT_CONF.JsonBox|{inline, component_name, name, default_value, conf, label}), "label-position": *, label: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), check: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), mode: *, expand: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), multi_select: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), inline: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), name: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), options: {component_name: string}, action: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), theme: *, data_url: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label}), group: (DEFAULT_CONF.BoolBox|{inline, component_name, name, default_value, conf, label}), check_url: (DEFAULT_CONF.TextBox|{inline, component_name, name, conf, label})}}
 */
const CUSTOM_CONF_COMPONENT_MAPPING = {
    name: DefaultTextBoxMeta,
    label: DefaultTextBoxMeta,
    component_name: utils.merge({data_url: URL.COMPONENT_CODE_LIST}, DefaultDropDownBoxMeta),
    conf: DefaultMinFormBoxMeta,
    inline: DefaultBoolBoxMeta,
    data_url: DefaultTextBoxMeta,
    delete_url: DefaultTextBoxMeta,
    multi_select: DefaultBoolBoxMeta,
    editable: DefaultBoolBoxMeta,
    options: {
        component_name: 'OptionsInput'
    },
    group: DefaultBoolBoxMeta,
    expand: DefaultBoolBoxMeta,
    "label-position": utils.merge({
        options: ['top-center', 'top-left', 'top-right', 'bottom-center', 'bottom-left', 'bottom-right']
    }, DefaultDropDownBoxMeta),
    action: DefaultTextBoxMeta,
    check: DefaultBoolBoxMeta,
    check_url: DefaultTextBoxMeta,
    theme: utils.merge({options: ['default', 'ambiance']}, DefaultRadioBoxMeta),
    lineNumbers: DefaultBoolBoxMeta,
    mode: utils.merge({options: ['text/x-mysql']}, DefaultDropDownBoxMeta)
};

export default CUSTOM_CONF_COMPONENT_MAPPING;
