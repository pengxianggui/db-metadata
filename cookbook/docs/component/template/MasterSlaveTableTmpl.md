# 主子表模板

`主子表模板 = (主搜索组件实例 + 主表单组件实例 + 主表格组件实例) + (子搜索组件实例 + 子表单组件实例 + 子表格组件实例) * n`

## 组件名

`MasterSlaveTableTmpl`

## 功能配置

```json
{
  "master": {
    "config": {
      "objectCode": null,
      "primaryKey": null
    },
    "instanceCodes": {
      "SearchView": null,
      "TableView": null,
      "FormView": null
    }
  },
  "slaves": [
    {
      "config": {
        "objectCode": null,
        "foreignPrimaryKey": null,
        "order": 0
      },
      "instanceCodes": {
        "SearchView": null,
        "TableView": null,
        "FormView": null
      }
    }
  ]
}
```

## props

### config

功能配置，即上面的json。优先级高于`fc`, 一般情况而言，只有当数据库中没有的功能配置，硬编码写死的，可以直接通过此属性传入。

### fc

功能编码，即 `featureCode`的简写。内部会自动获取fc代表的功能配置。

## 插槽

### operation-bar

主表顶部操作栏。参数见[TableView](/component/view/tableview.html#operation-bar-2)

### prefix-btn

主表顶部操作条`添加`按钮前的插槽。传递参数:

- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### add-btn

主表顶部操作条`添加`按钮的插槽。传递参数:

- `conf`:   `operation-bar.add`配置项
- `choseData`: 表格点选的数据
- `add`:    新增行为，可执行

### suffix-btn

主表顶部操作条`删除`按钮后的插槽。传递参数:

- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### buttons

主表操作列中按钮组的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置

### inner-before-extend-btn

主表操作列中按钮组前面的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置

### view-btn

主表操作列中`查看`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.view.conf`配置
- `view`:   按钮行为方法

### edit-btn

主表操作列中`编辑`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.edit.conf`配置
- `edit`:   按钮行为方法

### delete-btn

主表操作列中`删除`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.delete.conf`配置
- `delete`:   按钮行为方法

### inner-after-extend-btn

主表操作列中按钮组后面的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置


### {objectCode}-operation-bar

子表顶部操作栏。参数见[TableView](/component/view/tableview.html#operation-bar-2)

### {objectCode}-prefix-btn

子表顶部操作条`添加`按钮前的插槽。传递参数:

- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### {objectCode}-add-btn

子表顶部操作条`添加`按钮的插槽。传递参数:

- `conf`:   `operation-bar.add`配置项
- `choseData`: 表格点选的数据
- `add`:    新增行为，可执行

### {objectCode}-suffix-btn

子表顶部操作条`删除`按钮后的插槽。传递参数:

- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### {objectCode}-buttons

子表操作列中按钮组的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置

### {objectCode}-inner-before-extend-btn

子表操作列中按钮组前面的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置

### {objectCode}-view-btn

子表操作列中`查看`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.view.conf`配置
- `view`:   按钮行为方法

### {objectCode}-edit-btn

子表操作列中`编辑`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.edit.conf`配置
- `edit`:   按钮行为方法

### {objectCode}-delete-btn

子表操作列中`删除`按钮插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons.delete.conf`配置
- `delete`:   按钮行为方法

### {objectCode}-inner-after-extend-btn

子表操作列中按钮组后面的插槽。传递参数:

- `scope`: 当前记录数据, 见`el-table`文档
- `conf`:  `buttons`配置
