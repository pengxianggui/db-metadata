
export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |conf||object|-|-|
`;

export default {
    "component_name": "RowGrid",
    "name": "RowGrid",
    "label": "栅格布局",
    "conf": {
        "span": [12, 12],
        "show-line": false, // 显示虚线边框
    },
    "columns": {  // 处于该栅格布局下的组件元配置数据
        // "0": [{
        //     name: '',
        //     label: '',
        // }],
        // "1": [],
        // "2": [],
    }
}
