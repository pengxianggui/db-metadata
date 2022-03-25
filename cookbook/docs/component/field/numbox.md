# 数值框

## 组件名

`NumBox`

## 配置项

```json
{
  "name": "NumBox",
  "label": "数字框",
  "conf": {
    "controls": true,
    "placeholder": "请输入数值.."
  },
  "explain": ""
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
此项配置为ElementUI `el-input-number`原生配置`
