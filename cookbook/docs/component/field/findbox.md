# 查找框

## 组件名

`FindBox`

## 配置项

```json
{
  "name": "FindBox",
  "label": "查找框",
  "explain": "",
  "data_url": "/find/meta?objectCode={objectCode}&fieldCode={fieldCode}",
  "conf": {
    "clearable": true,
    "placeholder": "戳我展开搜索面板.."
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

### data_url
查找框点击时构建检索面板元数据的data_url，你无需配置此项。
但也许需要指定其中的占位变量, 一般情况下不用，因为dbmeta会为你将其中的变量名替换为正确的值。

### conf
此项配置为ElementUI `el-input-number`原生配置`
