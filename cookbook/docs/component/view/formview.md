# 表单组件
## 组件名
`FormView`

## 配置项
```json
{
  "name": "FormView",
  "action": "/form/doAdd?objectCode={objectCode}",
  "style": {
    "width": "60%"
  },
  "form_type": "ADD",
  "conf": {
    "label-width": "100px",
    "size": "medium",
    "disabled": false,
    "inline": false,
    "rules": {}
  },
  "columns": [],
  "layout": [],
  "buttons": {
    "show": true,
    "submit": {
      "show": true,
      "label": "提交",
      "conf": {
        "type": "primary"
      }
    },
    "cancel": {
      "show": true,
      "label": "取消",
      "conf": {
      }
    }
  }
}
```

### name
表单名, 此值会作为表单的ref值

### action
表单提交地址。通常，你无需更改此配置，dbmeta会依据表单类型(新增、更新还是查看)来自动替换此值，并相应替换其中的变量。 
但如果你需要将表单提交到自定义的接口上，可以更改此值，不过你最好调试下，了解表单的提交数据格式。以便接口开发。

### style
应用于表单上的样式。

:::warning
注意: `style.width` 比较特殊。当表单位于内置的弹窗中时，此值将会作用域弹窗上, 而表单将设置为百分百宽度。若
表单不位于内置弹窗组件中，那么此值就作用于表单上。

这是由于element弹窗无法被内部元素宽度撑大。
:::

### form_type
表单类型。可选值有三个: `ADD/UPDATE/VIEW`。分别表示表单是新增表单，更新表单，还是查看表单。毕竟后两者是需要
提供数据的。

### conf
ElementUI `el-form`原生配置。你可以在此属性中配置表单验证规则(`rules`)等。

### columns
这是表单中表单项的配置，每一个域组件配置，都可以作为此数组值中的一个元素。

### layout
表单的布局信息记录属性。一个标准的布局信息格式如下:

```json
{
  "layout": [
    {
      "name": "RowGrid19107",
      "label": "栅格布局",
      "component_name": "RowGrid",
      "sort": 1,
      "conf": {
        "span": [
          12,
          12
        ],
        "show-line": false
      },
      "columns": {
        "0": [
          "username"
        ],
        "1": [
          "password"
        ]
      }
    }
  ]
}
```
::: tip
通常你不用关注这个，因为DbMeta内置的表单引擎支持你拖拽生成一个表单, 其中布局信息会自动生成此格式数据。

你只有在基于`FormView`开发时，才可能需要了解。
:::

### buttons
见名知意。就是表单底下两按钮的配置，就不深入说明了。

> 目前还没有涉及action行为配置, 比如按钮执行时调用一些方法, 这些方法可配置。 敬请期待... 

## props
:::warning
除非你需要基于`FormView`开发，否则，你无需关注`props`。
:::

### meta
就是上面的配置项数据。
> 正式运行时, meta.columns是有值的。

### ic
实例编码。

优先级: `meta > ic`, 但这两者只需要一个！所以优先级也没什么意义。

### model
你也可以通过这个属性传入表单的值。

### formType
表单类型，和meta.formType含义一致，优先级更高。

### primaryKv
主键值或主键组键值对(当为联合主键时)

## 插槽
### action
表单底下量按钮的插槽。传递的属性有:
- `model` 表单数据
- `conf`  meta.buttons配置
- `submit` 提交方法, 可执行
- `cancel` 取消方法，可执行

### form-item-{item.name}
注意，这是一个具名可变的插槽。如果你的meta.columns中含有某个域组件配置为：

```json
  "name": "name",
  "label": "姓名",
  "explain": "",
  "conf": {
    "placeholder": "请输入姓名..",
    "clearable": true
  }
```

那么， 具名插槽: `form-item-name`就是`姓名`这个表单项的插槽，你可以通过这个具名插槽替换`姓名`表单项的内容。

:::warning
注意, 需要你自行提供 el-form-item标签
:::

插槽传递的属性有:
- `column`: 当前插槽对应的域组件配置
- `model`:  表单数据
