import {deepClone, isObject, isString} from "../../utils/common";

/**
 * 将栅格信息拍平，并提取到layout属性上
 *
 * eg:
 * <pre>
 {
        "objectCode": "meta_dict",
        "component_name": "FormView",
        "name": "FormView",
        "label": "表单模板",
        "width": "60%",
        "form_type": "ADD",
        "conf": {
          "label-width": "100px",
          "size": "medium",
          "disabled": false,
          "inline": false,
          "rules": {}
        },
        "columns": [
            {
              "name": "RowGrid0",
              "label": "栅格布局",
              "component_name": "RowGrid",
              "conf": {
                "span": [
                  12,
                  12
                ],
                "show-line": false
              },
              "columns": {
                "0": [
                  {
                    "conf": {
                      "rules": [
                        {
                          "type": "string",
                          "message": "[主键]是必须填写的",
                          "trigger": [
                            "blur"
                          ],
                          "required": true
                        }
                      ],
                      "maxlength": 32,
                      "placeholder": "请输入内容..",
                      "clearable": true
                    },
                    "name": "id",
                    "label": "主键",
                    "inline": false,
                    "default_value": "",
                    "component_name": "TextBox"
                  }
                ],
                "1": [
                  {
                    "conf": {
                      "maxlength": 32,
                      "placeholder": "请输入内容..",
                      "clearable": true
                    },
                    "name": "pid",
                    "label": "父层标志",
                    "inline": false,
                    "default_value": "",
                    "component_name": "TextBox"
                  }
                ]
              }
            }
        ],
        "layout": []
    }
 * </pre>
 * 拍扁后, 变成:
 *
 * <pre>
 {
        "objectCode": "meta_dict",
        "component_name": "FormView",
        "name": "FormView",
        "label": "表单模板",
        "width": "60%",
        "form_type": "ADD",
        "conf": {
          "label-width": "100px",
          "size": "medium",
          "disabled": false,
          "inline": false,
          "rules": {}
        },
        "columns": [
            {
                "conf": {
                  "rules": [
                    {
                      "type": "string",
                      "message": "[主键]是必须填写的",
                      "trigger": [
                        "blur"
                      ],
                      "required": true
                    }
                  ],
                  "maxlength": 32,
                  "placeholder": "请输入内容..",
                  "clearable": true
                },
                "name": "id",
                "label": "主键",
                "inline": false,
                "default_value": "",
                "component_name": "TextBox"
            },
            {
                "conf": {
                  "maxlength": 32,
                  "placeholder": "请输入内容..",
                  "clearable": true
                },
                "name": "pid",
                "label": "父层标志",
                "inline": false,
                "default_value": "",
                "component_name": "TextBox"
            }
        ],
        "layout": [
            {
              "name": "RowGrid0",
              "label": "栅格布局",
              "component_name": "RowGrid",
              "conf": {
                "span": [
                  12,
                  12
                ],
                "show-line": false
              },
              "columns": {
                "0": [
                  "id"
                ],
                "1": [
                  "pid"
                ]
              }
            }
        ]
    }
 * </pre>
 * 可以看到, 函数主要是改变instanceConfig入参的结构, 将fieldsMap下栅格嵌套的信息，提取到layout中, 然后fieldsMap替换为拍平后
 * 的字段配置信息。目的主要有两个:
 * 1. 后端接口(设计)限制, 导致fieldsMap为单纯的字段配置信息, 不支持嵌套
 * 2. 栅格的引入导致嵌套结构, 嵌套信息应当属于FormView容器自身, 而不是某个字段或某几个字段
 * @param formViewMeta
 */
export function gridInfoFattened(formMeta) {
    const {columns = {}, layout = []} = formMeta
    const fields = []

    try {
        for (let i = 0; i < columns.length; i++) {
            let rowGrid = extractField(columns, i, fields)
            if (isObject(rowGrid)) {
                layout.push(rowGrid)
            }
        }

        formMeta['columns'] = fields
        formMeta['layout'] = layout
    } catch (err) {
        console.log(err)
    }
    return formMeta
}



/**
 * 将拍平的栅格信息结构化到columns属性上。与 {@link gridInfoFattened} 正好相反
 * @param formViewMeta
 */
export function gridInfoStructured(formMeta) {
    const {columns = [], layout = []} = formMeta
    try {
        for (let i = 0; i < layout.length; i++) {
            let rowGrid = layout[i]
            restoreField(layout, i, columns)
            columns.push(rowGrid)
        }

        columns.sort((c1, c2) => c1.sort - c2.sort)
        formMeta['layout'] = [] // 防止提交时重复归并到layout
    } catch (err) {
        console.log(err)
    }
    refreshColumnsSort(formMeta.columns)
    return formMeta
}

/**
 * 按照columns的顺序为item.sort 初始化值。仅需对第一层进行排序即可, 子层由栅格信息记录了具体位置
 * @param columns
 */
export function refreshColumnsSort(columns) {
    for (let i = 0; i < columns.length; i++) {
        columns[i].sort = i
    }
}

const restoreField = function (obj, key, formColumns) {
    const rowGrid = obj[key]
    if (isString(rowGrid)) {
        let index = formColumns.findIndex(element => element.name === rowGrid)
        if (index >= 0) { // 也可能此字段被逻辑控制隐藏、禁用而找不到了
            obj[key] = formColumns[index]
            formColumns.splice(index, 1)
        }
        return
    }

    const {columns = []} = rowGrid
    Object.keys(columns).forEach(k => {
        for (let i = 0; i < columns[k].length; i++) {
            restoreField(columns[k], i, formColumns)
        }
    })
}

const extractField = function (obj, key, fields) {
    const formItem = obj[key]
    const {component_name: componentName, columns = []} = formItem
    if (componentName === 'RowGrid') {
        Object.keys(columns).forEach(k => {
            for (let i = 0; i < columns[k].length; i++) {
                extractField(columns[k], i, fields)
            }
        })
        return formItem
    } else {
        fields.push(deepClone(formItem))
        obj[key] = formItem.name
    }
}


/**
 * TODO 测试 {@link gridInfoFattened} 和 {@link gridInfoStructured}
 */
