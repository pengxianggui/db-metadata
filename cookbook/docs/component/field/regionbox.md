# 省市框

## 组件名

`RegionBox`

## 配置项

```json
{
  "name": "RegionBox",
  "label": "地区/省市框",
  "explain": "",
  "conf": {
    "type": "default",
    "level": 2
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
此项配置为第三方组件 `vue-address`原生配置`。

#### conf.type
省市选择有两种形态:

- 默认形态: 省市区 在一个下拉控件中, 多级展开。
- 级联形态: 省市区 分为多个下拉控件, 级联联动。

此项配置就是代表你需要哪种形态。

可选值有: `default`/`cascader`

#### conf.level
有时候，你只需要选择省，或者选择省市。那么可以通过此项配置选择深度。

可选值有: `0/1/2`, 默认为`2`
