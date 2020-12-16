export const ConfDesc = `
> 此组件需配合db-metadata后端接口

|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|inline|声明为内敛元素,当存在于FormView中时有效|boolean|true/false|false|
|data_url|获取弹窗数据的url|String|-|/table/list/{objectCode}|
|conf|支持{'clearable': true, 'placeholder': ''}|object|object|-|
`;

export const panelMeta = {
    "component_name": "FindPanel",
    "name": "FindPanel",
    "label": "查找面板",
    "data_url": "/table/list/{objectCode}",
    "columns": [// {
        //     component_name: 'TextBox',
        //     name: 'id',
        //     label: 'ID',
        //     searchable: true,    // 搜索面板支持该字段搜索, 若为true, 则会在FindPanel的搜索面板中生成搜索控件
        //     conf: {
        //         width: '',
        //         sortable: true,  // 表格中可否根据此列进行排序
        //         //...
        //     }
        // },
        // {
        //     component_name: 'TextBox',
        //     name: 'name',
        //     label: '姓名',
        //     searchable: true,    // 搜索面板支持该字段搜索, 若为true, 则会在FindPanel的搜索面板中生成搜索控件
        //     conf: {
        //         width: '',
        //         sortable: true,  // 表格中可否根据此列进行排序
        //         //...
        //     }
        // }
    ]
};

export default {
    "component_name": "FindBox",
    "name": "FindBox",
    "label": "查找框",
    "data_url": "/meta/fields/{objectCode}", // 这个data_url响应的是FindPanel的meta, 也可以是TableList的meta
    "inline": false,
    "conf": {
        "clearable": true,
        "placeholder": "戳我展开搜索面板.."
    }
}
