import {pageNumArea} from '../../config'

export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |conf|codemirror的原生配置项|object|-|-|
    |pagination|分页配置对象|object|-|{'page-size': ` + pageNumArea[0] + `, 'page-sizes': ` + pageNumArea + `, 'current-size': 1, 'layout': 'total, prev, pager, next'}|
`;

export default {
    "component_name": "DataList",
    "name": "DataList",
    "label": "列表",
    "data_url": "/data/list/{objectCode}",
    "conf": {
        "label-props": {
            "label": "label"
        }
    },
    "pagination": { // element ui配置
        "page-size": pageNumArea[0],
        "page-sizes": pageNumArea,
        "current-page": 1,
        "layout": "total, prev, pager, next"
    }
}
