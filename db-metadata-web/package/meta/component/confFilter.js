import {isLayoutComp} from "../form-builder/relate/componentData";

/**
 * 字典。标识了组件实例数据编辑时，在什么容器下、什么组件的哪个元数据配置项不展示。
 * 比如，再FormView容器下, 容器实例配置的columns配置项不展示。
 * @type {{FormView: {VIEW_EXCLUDES: string[], FIELD_EXCLUDES: {COMMON: *[]}}}}
 */
const EXCLUDE_MAP = {
    FormView: {
        VIEW_EXCLUDES: ['component_name', 'columns', 'name', 'group', 'objectCode', 'objectPrimaryKey', 'layout', 'inline', 'form_type'],
        FIELD_EXCLUDES: {
            COMMON: ['group', 'objectCode', 'objectPrimaryKey'],
        },
        LAYOUT_EXCLUDES: ['columns']
    },
    SearchView: {
        VIEW_EXCLUDES: ['conf'],
        FIELD_EXCLUDES: {
            COMMON: ['group']
        }
    }
}

/**
 * 判断某些控件的配置项是否显示
 * @param meta 组件元数据配置
 * @param key 带判断的配置项(此配置项是meta中的某个key)
 * @param viewComponentName meta对应组件所处的容器组件
 */
export default function (meta, key, viewComponentName) {
    const componentName = meta['component_name']

    if (!EXCLUDE_MAP.hasOwnProperty(viewComponentName)) {
        return true;
    }

    let excludes = []
    if (isLayoutComp(componentName)) { // 布局组件
        excludes.push(...(EXCLUDE_MAP[viewComponentName].LAYOUT_EXCLUDES))
    } else if (componentName == viewComponentName) { // 容器组件
        excludes.push(...(EXCLUDE_MAP[viewComponentName].VIEW_EXCLUDES))
    } else {
        excludes.push(...(EXCLUDE_MAP[viewComponentName].FIELD_EXCLUDES.COMMON))
        if (EXCLUDE_MAP[viewComponentName].FIELD_EXCLUDES.hasOwnProperty(componentName)) {
            excludes.push(...(EXCLUDE_MAP[viewComponentName].FIELD_EXCLUDES[componentName]))
        }
    }

    return excludes.indexOf(key) <= -1
}
