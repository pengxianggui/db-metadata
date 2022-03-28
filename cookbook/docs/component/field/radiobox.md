# 单选框

## 组件名

`RadioBox`

## 配置项

```json
{
  "name": "RadioBox",
  "label": "单选框",
  "data_url": "/dict?name=",
  "options": [],
  "conf": {},
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

### data_url

动态选项配置。选项数据来源的接口url。响应的数据格式必须是:

```json
{
  "data": [
    {
      "key": "key1",
      "value": "value1"
    },
    {
      "key": "key2",
      "value": "value2"
    }
  ]
}
```

### options

静态选项配置。值为数组，且格式为:

```json
[
  {
    "key": "key1",
    "value": "value1"
  },
  {
    "key": "key2",
    "value": "value2"
  }
]
```

:::warning
`options`的优先级高于`data_url`
:::

### conf

此项配置为ElementUI `el-radio`原生配置`
