import {elementVersion} from "../../config";

export const ConfDesc = `
|配置项|说明|类型|可选值|默认值|
|-----|---|----|----|-----|
|component_name|渲染组件名|string|组件列表|-|
|name|name属性|string|-|-|
|label|该实例标签名,表现为表单域的label|string|-|-|
|expand|是否默认展开搜索面板|boolean|true/false|false|
|label-position|收缩开展开关按钮位置|string|top-left/top-center/top-right/bottom-left/bottom-center/bottom-right|top-center|
|directly_trigger|直接触发的字段name, 即change后无需点击搜索按钮可直接触发搜索action|Array|字段名组成的字符串数组|
|conf|ElementUI(` + elementVersion + `)中el-form的原生配置项|object|-|-|
`;

export default {
    "component_name": "SearchView",
    "name": "SearchView",
    "label": "搜索面板",
    "expand": false,
    "label-position": "top-center",
    "conf": {
        "label-width": '80px',
        "size": 'mini', // medium|small|mini
    },
    "columns": [],
    "directly_trigger": [], // 直接触发的字段name, 即change后无需点击搜索按钮可直接触发搜索action
    "explain": "" // 字段解释
}

/**
 * meta数据得到后的回调
 * @param meta
 */
export const assembleMeta = function (meta) {
    const {columns = []} = meta

    columns.forEach(colMeta => {
        const {component_name} = colMeta
        this.$merge(colMeta, {
            conf: {
                clearable: true
            }
        });

        switch (component_name) {
            case 'DateBox':
                this.$reverseMerge(colMeta, {"conf": {"is-range": true, "type": 'daterange'}})
                break;
            case 'TimeBox':
                this.$reverseMerge(colMeta, {"conf": {"is-range": true, "type": "timerange"}})
                break;
            case 'DateTimeBox':
                this.$reverseMerge(colMeta, {"conf": {"is-range": true, "type": 'datetimerange'}})
                break;
            case 'NumBox': // NumBox无法设置"比较操作符"插槽, 改为TextBox
                colMeta.component_name = 'TextBox'
                break;
        }
    })
}
