import utils from "../../utils";

const defaultMeta = {
    "component_name": "RichTextBox",
    "name": "RichTextBox",
    "label": "富文本",
    "conf": {
        "images_upload_url": "/file/upload/rich-text?objectCode={objectCode}&fieldCode={fieldCode}", // 富文本的上传路径
        "files_upload_url": "/file/upload/rich-text?objectCode={objectCode}&fieldCode={fieldCode}" // 富文本的上传路径
    },
    "explain": ""
}

export default defaultMeta;

export const callback = function (objectCode, fieldCode) {
    let meta = {}
    utils.merge(meta, defaultMeta)
    utils.reverseMerge(meta, {
        conf: {
            images_upload_url: utils.compile(defaultMeta.conf.images_upload_url, {
                objectCode: objectCode,
                fieldCode: fieldCode
            }),
            files_upload_url: utils.compile(defaultMeta.conf.files_upload_url, {
                objectCode: objectCode,
                fieldCode: fieldCode
            })
        }
    })
    return meta
}
