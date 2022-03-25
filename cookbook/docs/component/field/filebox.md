# 文件上传框

## 组件名

`FileBox`

## 配置项

```json
{
  "name": "FileBox",
  "label": "文件上传框",
  "seats": [""],
  "explain": "",
  "conf": {
    "action": "/file/upload?objectCode={objectCode}&fieldCode={fieldCode}",
    "drag": false,
    "tip": "上传文件限制不超过2M",
    "auto-upload": true,
    "show-file-list": true,
    "limit": 1,
    "multiple": false
  }
}
```

### name

当容器组件为表单(FormView)时, name表示此表单项的prop, 必须与表单数据model中对应的key一致。

### label

当容器组件为表单时(FormView)，label表示表单项的中文显示。

当容器组件为TableView或TableTreeView时，label表示为表格header中文列名。

### seats

`FileBox`支持多文件上传。如果需要多个文件，则指定seats。可以这么理解，一个上传文件一个位子，如果需要
上传多个文件，就设置多个位子。可以为'位子'指定名字，就是seats值，这个名字将显示在文件上传位下方。

### explain
说明。

容器组件为表单时(FormView)，表示表单项目中文显示旁的补充说明。

### conf
此项配置为ElementUI `el-upload`原生配置`。

:::tip
`conf.action`的值是dbmeta内置接收上传文件的接口地址, 如果需要上传到自定义接口，可以更改此项配置。
不过，接口记得返回上传的资源路径。
:::
