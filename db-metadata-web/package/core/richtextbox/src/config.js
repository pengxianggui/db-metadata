/**
 * tinymce富文本编辑器的配置
 * @type {{toolbar: string, menubar: boolean, plugins: [string, string, string], height: number}}
 */
export const EditorConf = {
    height: 500,
    menubar: false,
    plugins: [
        'advlist autolink lists link image charmap print preview anchor',
        'searchreplace visualblocks code fullscreen',
        'insertdatetime media table paste code help wordcount'
    ],
    toolbar:
        'undo redo | formatselect | bold italic backcolor | \
        alignleft aligncenter alignright alignjustify | \
        bullist numlist outdent indent | removeformat | help'
}

export const apiKey = 'no-api-key'