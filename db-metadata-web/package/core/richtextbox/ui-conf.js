// TODO 完善Tinymce富文本可配置的说明文档
export const ConfDesc = `
    |配置项|说明|类型|可选值|默认值|
    |-----|---|----|----|-----|
    |component_name|渲染组件名|string|组件列表|-|
    |name|name属性|string|-|-|
    |label|该实例标签名,表现为表单域的label|string|-|-|
    |conf|请参考<a target="_blank" href="http://tinymce.ax-z.cn/configure/integration-and-setup.php">tinymce</a>的原生配置项|object|-|-|
`;

export default {
    "component_name": "RichTextBox",
    "name": "RichTextBox",
    "label": "富文本",
    'sort': 0,
    "conf": {
        "images_upload_url": "/file/upload/richText" // 富文本的上传路径
    },
    "explain": "" // 字段解释
}
