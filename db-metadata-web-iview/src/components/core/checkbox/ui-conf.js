import APPConf from "@/config";

export const ConfDesc1 = {
    'component_name': '选择组件列表',
    'name': '实例名, 即该组件实例的name, 一般认为就是字段名',
    'label': '该实例标签名,表现为表单域的label',
    'inline': '是否为内联元素, 为true则表示该控件不会独占一行, 若表单中下一个控件的此元素值也为true, 则表现为两个控件位列一行',
    "data_url": "选项列表的数据接口, 请配置url. 注意响应的数据格式应当是数组, 数组内容为{key:value}对象",
    "options": "配置选项数据, 应当为一个数组, 数组内容为{key: value}对象. 注意当data_url和options同时存在时, data_url拥有更高优先级",
    'conf': '配置对象, 其中的具体配置支持 ElementUI(' + APPConf.elementVersion + ')中 el-checkbox的配置'
};

export const ConfDesc = ``

export default {
    "component_name": "CheckBox",
    "name": "CheckBox",
    "label": "多选框",
    "inline": true,
    "data_url": "/dict/yn",
    "options": [
        // {"key":"value"}
    ],
    "conf": {}
}