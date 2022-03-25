# 表格组件
基于`el-table`实现。

## 组件名
`TableView`

## 配置项
```json
{
    "name": "TableView",
    "data_url": "/table/list?objectCode={objectCode}",
    "delete_url": "/table/delete?objectCode={objectCode}&{primaryKvExp}",
    "multi_select": true,
    "conf": {
        "default-sort": {"prop": "id", "order": "descending"},
        "highlight-current-row": true,
        "size": "medium"
    },
    "operation-bar": {
        "show": true,
        "group": true,
        "style": {},
        "conf": {
            "size": "mini"
        },
        "add": {
            "show": true,
            "text": "新增",
            "conf": {
                "size": "mini",
                "type": "primary",
                "icon": "el-icon-document-add"
            }
        },
        "delete": {
            "show": true,
            "text": "删除",
            "conf": {
                "size": "mini",
                "type": "danger",
                "icon": "el-icon-delete-solid"
            }
        }
    },
    "columns": [],
    "operation-column": {
        "show": true,
        "conf": {
            "fixed": "right",
            "width": "180"
        },
        "style": {},
        "buttons": {
            "show": true,
            "group": true,
            "style": {},
            "conf": {
                "size": "mini"
            },
            "view": {
                "show": true,
                "text": "",
                "conf": {
                    "size": "mini",
                    "type": "success",
                    "icon": "el-icon-view"
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
                    "icon": "el-icon-delete"
                }
            }
        }
    },
    "pagination": {
        "show": true,
        "style": {

        },
        "conf": {
            "background": true,
            "page-size": 10,
            "page-sizes": [5, 10, 20, 50, 100, 200],
            "current-page": 1,
            "layout": "total, sizes, prev, pager, next, jumper"
        }
    }
}
```

### name
会作为内部`el-table`的ref name。

### data_url
表格数据接口地址。

### delete_url
表格数据删除操作的接口地址。

### multi_select
是否支持多选。

### conf
`el-table`原生配置项目

### operation-bar
表格顶部操作栏的配置。见名知意，就不深入了

### columns
表格内部每一列的配置，即为域配置。
> 表格虽然不能编辑，但是使用域配置还是有意义的，目前在表格中，域配置绝多配置项都没什么意义，但是考虑表格也可以支持直接编辑，
> 因此，保留域配置中的所有配置项。

### operation-column
表格操作列的配置。见名知意，就不深入了。

### pagination
分页配置。`pagination.conf`是`el-pagination`原生配置内容。

## props
### filterParams
表格数据的过滤入参，为http过滤而非前端过滤。此值用于`SearchView`参数传递。

## 插槽
### operation-bar
顶部操作条的插槽。传递参数:
- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### prefix-btn
顶部操作条`添加`按钮前的插槽。传递参数:
- `conf`:   `operation-bar`配置项
- `choseData`: 表格点选的数据

### add-btn
顶部操作条`添加`按钮的插槽。传递参数:
- `conf`:   `operation-bar.add`配置项
- `choseData`: 表格点选的数据
- `add`:    新增行为，可执行

### batch-delete-btn
顶部操作条`删除`按钮的插槽。传递参数:
- `conf`:   `operation-bar.delete`配置项
- `choseData`: 表格点选的数据
- `batchDelete`:    新增行为，可执行

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

### pagination
底部分页条插槽。传递参数:
- `pageModel`: 分页数据模型对象。
