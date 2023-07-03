import utils from '../../utils'

const defaultMeta = {
    "component_name": "ImgBox",
    "name": "ImgBox",
    "label": "图片上传框",
    "seats": [],
    "conf": {
        "action": "/file/upload?objectCode={objectCode}&fieldCode={fieldCode}",
        "drag": false,
        "list-type": "picture-card",
        "auto-upload": true,
        "show-file-list": true,
        "limit": 1,
        "multiple": false,  // 暂时单选
        "accept": "image/*" // 只接受图片的MIME
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
