# 迷你表单

迷你表单框是`JsonBox`的另一种形式, 迷你表单框会依据传入的`v-model`json值, 根据json中value的值类型
推导其控件, 从而推导一个表单。它内置了一个开关，用来切换`JsonBox`和`MiniFormBox`两种形态

## 组件名

`MiniFormBox`

## 配置项

```json
{
  "name": "MiniFormBox",
  "label": "迷你表单框",
  "explain": "",
  "controls": true,
  "conf": {
    "label-width": "80px",
    "size": "mini",
    "disabled": false,
    "inline": false
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

### controls
是否显示切换形态按钮

### conf
此项配置为ElementUI `el-form`原生配置`
