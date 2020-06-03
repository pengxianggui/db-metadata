import APPConf from '@/config'

export const ConfDesc = {
    'component_name': '选择组件列表',
    'name': '实例名, 即该组件实例的name, 一般认为就是字段名',
    'label': '该实例标签名,表现为表单域的label',
    'default_value': '默认为false',
    'inline': '是否为内联元素, 为true则表示该控件不会独占一行, 若表单中下一个控件的此元素值也为true, 则表现为两个控件位列一行',
    'conf': '配置对象, 其中的具体配置支持 ElementUI(' + APPConf.elementVersion + ')中 el-checkbox的配置'
};

export default {
    'component_name': 'BoolBox',
    'name': 'BoolBox',
    'label': '布尔框',
    'default_value': false,
    'inline': true,
    'conf': {}
}