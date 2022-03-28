# 树组件
基于ElementUI `el-tree`组件

## 组件名
`TreeView`

## 配置项
```json
{
  "name": "Tree",
  "label": "树型列表",
  "data_url": "/table/tree/{objectCode}",
  "editable": false,
  "conf": {
    "node-key": "id",
    "highlight-current": true,
    "default-expand-all": false,
    "check-on-click-node": false,
    "expand-on-click-node": false,
    "show-checkbox": false,
    "default-expanded-keys": [],
    "default-checked-keys": [],
    "accordion": false,
    "indent": 16,
    "icon-class": "el-icon-caret-right",
    "draggable": false,
    "props": {
      "label": "label",
      "children": "children"
    }
  },
  "operation-bar": {
    "show": true,
    "group": true,
    "style": {},
    "conf": {
      "size": "mini",
      "type": "primary"
    },
    "add": {
      "show": true,
      "text": "",
      "conf": {
        "size": "mini",
        "type": "primary",
        "icon": "el-icon-document-add"
      }
    },
    "edit": {
      "show": true,
      "text": "",
      "conf": {
        "size": "mini",
        "type": "primary",
        "icon": "el-icon-edit"
      }
    },
    "delete": {
      "show": true,
      "text": "",
      "conf": {
        "size": "mini",
        "type": "danger",
        "icon": "el-icon-delete-solid"
      }
    }
  }
}
```


### name
会作为内部`el-tree`的ref name。

### data_url
表格数据接口地址。

### editable
是否可编辑。只有开启，才能新增、修改、删除节点数据。

### conf
ElementUI `el-tree`组件配置。

### operation-bar
顶部操作栏配置。见名知意，不深入解释了。


## 插槽
### operation-bar
顶部操作条的插槽。传递参数:
- `conf`:   `operation-bar`配置项
- `choseData`: Array, 树点选的数据

### prefix-btn
顶部操作条`添加`按钮前的插槽。传递参数:
- `conf`:   `operation-bar`配置项
- `activeData`: 树点选的数据
- `choseData`: Array, 树选中的数据

### add-btn
顶部操作条`添加`按钮的插槽。传递参数:
- `conf`:   `operation-bar.add`配置项
- `activeData`: 树点选的数据
- `choseData`: Array, 树选中的数据
- `add`:    新增行为，可执行

### batch-delete-btn
顶部操作条`删除`按钮的插槽。传递参数:
- `conf`:   `operation-bar.delete`配置项
- `activeData`: 树点选的数据
- `choseData`: Array, 树选中的数据
- `batchDelete`:    新增行为，可执行

### suffix-btn
顶部操作条`删除`按钮后的插槽。传递参数:
- `conf`:   `operation-bar`配置项
- `activeData`: 树点选的数据
- `choseData`: Array, 树选中的数据
