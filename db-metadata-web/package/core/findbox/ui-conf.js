import utils from "../../utils";

export const panelMeta = {
    "component_name": "FindPanel",
    "name": "FindPanel",
    "label": "查找面板",
    "data_url": "/table/list?objectCode={objectCode}",
    "columns": []
};

const defaultMeta = {
    "component_name": "FindBox",
    "name": "FindBox",
    "label": "查找框",
    "data_url": "/find/meta?objectCode={objectCode}&fieldCode={fieldCode}",
    "conf": {
        "clearable": true,
        "placeholder": "戳我展开搜索面板.."
    },
    "explain": ""
}

export default defaultMeta

/**
 * 构造动态meta
 * @param objectCode
 * @param fieldCode
 * @returns {{}}
 */
export const callback = function (objectCode, fieldCode) {
    let meta = {}
    utils.merge(meta, defaultMeta)
    utils.reverseMerge(meta, {
        data_url: utils.compile(defaultMeta.data_url, {
            objectCode: objectCode,
            fieldCode: fieldCode
        })
    })
    return meta
}
