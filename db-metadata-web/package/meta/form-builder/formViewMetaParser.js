import utils from '../../utils'
import {isLayoutComp} from "./relate/componentData";

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
            if (utils.isObject(rowGrid)) {
                layout.push(rowGrid)
            }
        }

        formMeta['columns'] = fields
        formMeta['layout'] = layout
    } catch (err) {
        console.error(err)
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

        columns.sort((c1, c2) => {
            const {sort: sort1 = 9999} = c1
            const {sort: sort2 = 9999} = c2
            return sort1 - sort2
        })
        formMeta['layout'] = [] // 防止提交时重复归并到layout
    } catch (err) {
        console.error(err)
    }
    refreshColumnsSort(formMeta.columns)
    return formMeta
}

/**
 * 将一个新的域配置更新到formMeta中
 * @param formMeta 表单的meta配置，columns有内容，是一个可能含有栅格布局的层级结构内容
 * @param fieldMeta 需要替换进去的新的域配置值
 */
export function setFieldConfig(formMeta, fieldMeta) {

}

/**
 * 从表单实例配置中读取指定域名称的域配置。深度优先遍历
 * @param formMeta
 * @param fieldName
 */
export function getFieldConfig(formMeta, fieldName) {
    if (utils.isEmpty(formMeta) || utils.isEmpty(fieldName)) {
        return null;
    }

    const {columns = []} = formMeta
    for (let i = 0; i < columns.length; i++) {
        let {name, component_name} = columns[i]
        if (name === fieldName) {
            return columns[i]
        }

        if (isLayoutComp(component_name)) {
            let fieldMeta = getFieldConfig(columns[i], fieldName)
            if (!utils.isEmpty(fieldMeta)) {
                return fieldMeta
            }
        }
    }
    return null
}

const restoreField = function (obj, key, formColumns) {
    const rowGrid = obj[key]
    if (utils.isString(rowGrid)) {
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
        fields.push(utils.deepClone(formItem))
        obj[key] = formItem.name
    }
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

/**
 * 判断是否为空的栅格布局
 * @param gridRowMeta
 */
export function isEmptyGridRow(gridRowMeta) {
    const {component_name: componentName, columns = []} = gridRowMeta

    if (!isLayoutComp(componentName)) {
        return false;
    }

    const keys = Object.keys(columns)
    for (let i = 0; i < keys.length; i++) {
        let k = keys[i]
        let v = columns[k]
        for (let j = 0; j < v.length; j++) {
            if (!isEmptyGridRow(v[j])) {
                return false
            }
        }
    }
    return true;
}

/**
 * 判断容器组件是否可显示: 当为空栅格，或栅格下存在显示的(hidden!==true)域组件时, 即为可显示。如果不是栅格配置，也返回true
 * @param gridRowMeta
 */
export const isLayoutCompShow = (gridRowMeta) => {
    const {component_name: componentName, columns = []} = gridRowMeta

    if (!isLayoutComp(componentName)) {
        return true;
    }

    if (isEmptyGridRow(gridRowMeta)) { // 空栅格也必须显示，否则无法添加栅格了
        return true
    }

    const keys = Object.keys(columns)
    for (let i = 0; i < keys.length; i++) {
        let k = keys[i]
        let v = columns[k]
        for (let j = 0; j < v.length; j++) {
            const {hidden, component_name: componentName} = v[j]
            if (isLayoutComp(componentName)) {
                if (isLayoutCompShow(v[j])) {
                    return true
                }
            } else {
                if (hidden !== true) {
                    return true
                }
            }
        }
    }

    return false;
}

/**
 * TODO 测试 {@link gridInfoFattened} 和 {@link gridInfoStructured}
 */
