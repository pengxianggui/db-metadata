/**
 * tinymce富文本编辑器的配置
 * 更多配置, 参见: https://www.tiny.cloud/docs/configure/editor-appearance
 * 图片上传:  https://www.tiny.cloud/docs/general-configuration-guide/upload-images/
 *
 * @type {{toolbar: string, menubar: boolean, plugins: [string, string, string], height: number}}
 */
export const EditorConf = {
    height: 500,
    menubar: false,
    plugins: [
        'advlist autolink lists link image charmap print preview anchor',
        'searchreplace visualblocks code codesample fullscreen',
        'insertdatetime media table paste code help wordcount'
    ],
    toolbar: `
        preview | undo redo | formatselect | fontselect | fontsizeselect | forecolor | bold italic backcolor |
        indent outdent | alignleft aligncenter alignright alignjustify | 
        bullist numlist | table image link | help`,
    toolbar_mode: 'floating',
    branding: false,
    draggable_modal: true
}

// apiKey是TinyMCE富文本提供的一个token令牌, 用于使用更多高级别的插件, 若不配置此值, 富文本的基础功能仍然可以使用，但是每次初始化都会
// 弹出一个引导用户注册设置的浮框，用户体验不佳
// 更多Tiny内容参考: https://www.tiny.cloud/
export const apiKey = 'j20dq1d7wwyj5euhai46gfrn5m76zzasiwjexdwid3ydcvci'
