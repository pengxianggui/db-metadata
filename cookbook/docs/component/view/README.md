# 容器

DbMeta目前一共内置的容器组件有5个:

1. `FormView`   表单组件
1. `SearchView` 搜索面板组件
1. `TableView`  普通表格组件
1. `TableTreeView` 树表组件
1. `TreeView`   树组件

:::tip
自定义组件扩展机制，请期待。
:::

容器组件 + 元对象 可以成为一套实例配置。公式如下:

`容器实例配置 = 元对象 + 容器组件`

容器实例配置有唯一编码: `instanceCode`(`ic`)
