# 富文本框

## 组件名

`RichTextBox`

## 配置项

```json
{
  "name": "RichTextBox",
  "label": "富文本",
  "explain": "",
  "conf": {
    "images_upload_url": "/file/upload/richText",
    "toolbar": [],
    "menubar": "file edit insert view format table",
    "width": "auto",
    "height": 360
  }
}
```

### name

当容器组件为表单(FormView)时, name表示此表单项的prop, 必须与表单数据model中对应的key一致。

### label

当容器组件为表单时(FormView)，label表示表单项的中文显示。

当容器组件为TableView或TableTreeView时，label表示为表格header中文列名。

### explain
说明。

容器组件为表单时(FormView)，表示表单项目中文显示旁的补充说明。

### conf
此项配置为第三方组件 [`vue-tinymce-text`](https://www.npmjs.com/package/vue-tinymce-text)原生配置。
源码地址: [传送门](https://gitlab.asoco.com.cn/hthj-wheel/tinymce-text)

#### conf.images_upload_url
富文本中图片上传地址。如果需要自定义，记得返回上传后的资源路径。

#### conf.toolbar
工具栏配置。默认空，则是全部都有。

可选值: `['preview | undo redo | bold italic underline strikethrough forecolor backcolor | alignleft aligncenter alignright outdent indent blockquote | removeformat subscript superscript | hr bullist numlist | table | link image media | codesample emoticons | pagebreak | fullscreen']`

#### conf.menubar
顶部的菜单栏。可选值: `"file edit insert view format table"`

#### conf.width
富文本的宽度。默认auto， 自动撑开。可以支持纯数字(将识别为`px`)和标准的像素宽度值(如: `100px`)

#### conf.height
富文本的高度。默认360, 只支持纯数字。
