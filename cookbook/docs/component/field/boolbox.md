# 布尔框

## 组件名

`BoolBox`

## 支持的值

```js
[
    false, true,
    "false", "true",
    0, 1,
    "0", "1",
    "f", "t",
    null, true,
    undefined, true,
    '否', '是'
]
```
> 奇数下标代表 false, 偶数下标代表true

## 配置项

```json
{
  "name": "BoolBox",
  "label": "布尔框",
  "default_value": false,
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

### default_value

默认值, 为false。




