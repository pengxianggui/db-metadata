# 树形表模板

`树形表模板 = 搜索组件实例 + 表单组件实例 + 树表组件实例`

## 组件名

`TreeSingleGridTmpl`

## 功能配置

```json
{
  "config": {
    "objectCode": null
  },
  "instanceCodes": {
    "SearchView": null,
    "TableTreeView": null,
    "FormView": null
  }
}
```

## props

### config

功能配置，即上面的json。优先级高于`fc`, 一般情况而言，只有当数据库中没有的功能配置，硬编码写死的，可以直接通过此属性传入。

### fc

功能编码，即 `featureCode`的简写。内部会自动获取fc代表的功能配置。

## 插槽

### operation-bar

顶部操作栏。参数见[TableView](/component/view/tableview.html#operation-bar-2)

### prefix-btn

顶部操作条`添加`按钮前的插槽。传递参数:

- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### add-btn

顶部操作条`添加`按钮的插槽。传递参数:

- `conf`:   `operation-bar.add`配置项
- `choseData`: 表格点选的数据
- `add`:    新增行为，可执行

### suffix-btn

顶部操作条`删除`按钮后的插槽。传递参数:

- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### buttons

操作列中按钮组的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置

### inner-before-extend-btn

操作列中按钮组前面的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置

### view-btn

操作列中`查看`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.view.conf`配置
- `view`:   按钮行为方法

### edit-btn

操作列中`编辑`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.edit.conf`配置
- `edit`:   按钮行为方法

### delete-btn

操作列中`删除`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.delete.conf`配置
- `delete`:   按钮行为方法

## inner-after-extend-btn

操作列中按钮组后面的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置
