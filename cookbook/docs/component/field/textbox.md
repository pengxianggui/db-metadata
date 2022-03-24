# 单行文本域

## 组件名

`TextBox`

## 配置项

```json
{
  "name": "TextBox",
  "label": "文本框",
  "explain": "",
  "conf": {
    "placeholder": "请输入内容..",
    "clearable": true
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

容器组件为表单时(FormView)，表示表单项目中文显示旁的问号说明。

### conf
此项配置为ElementUI `el-input`原生配置。
