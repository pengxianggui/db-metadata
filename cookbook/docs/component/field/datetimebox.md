# 日期时间框

## 组件名

`DateTimeBox`

## 配置项

```json
{
  "name": "DateTimeBox",
  "label": "日期时间框",
  "explain": "",
  "conf": {
    "value-format": "yyyy-MM-dd HH:mm:ss",
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

容器组件为表单时(FormView)，表示表单项目中文显示旁的补充说明。

### conf
此项配置为ElementUI `el-date-picker`原生配置`, 内置`type=datetime`。

:::warning
注意: `conf.value-format` 格式最好不要更改, 这个值的格式涉及到服务端的时间序列化, 
dbmeta为`Date、TimeStamp、DateTime、LocalDateTime`默认的日期序列化格式就是`yyyy-MM-dd HH:mm:ss`
:::
