import utils from "../../utils";

const defaultMeta = {
    "component_name": "FileBox",
    "name": "FileBox",
    "label": "文件上传框",
    "seats": [],
    "conf": {
        "action": "/file/upload?objectCode={objectCode}&fieldCode={fieldCode}",
        "drag": false,
        "tip": "上传文件限制不超过2M",
        "auto-upload": true,
        "show-file-list": true,
        "limit": 1,
        "multiple": false
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
        conf: {
            action: utils.compile(defaultMeta.conf.action, {
                objectCode: objectCode,
                fieldCode: fieldCode
            })
        }
    })
    return meta
}
