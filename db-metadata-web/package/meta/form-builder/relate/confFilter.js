import DefaultFormViewMeta from '../../../core/formview/ui-conf'
import {isLayoutComp} from "./componentData";

// inline 由 栅格布局支持后移除
const FORM_EXCLUDES = [
    'columns', 'buttons', 'name',
    'group', 'objectCode', 'objectPrimaryKey',
    'component_name', 'layout', 'inline', 'form_type']; // 表单隐藏的配置项

const FIELD_EXCLUDES = ['group', 'objectCode', 'objectPrimaryKey', 'sort', 'inline']; // 字段隐藏的配置项

const LAYOUT_EXCLUDES = ['columns'] // 布局组件隐藏的配置项

/**
 * 判断某些控件的配置项是否显示
 * @param componentName
 * @param key
 */
export default function (componentName, key) {
    if (componentName === DefaultFormViewMeta.component_name) {
        return FORM_EXCLUDES.indexOf(key) <= -1
    } else if (isLayoutComp(componentName)) {
        return LAYOUT_EXCLUDES.indexOf(key) <= -1
    } else {
        return FIELD_EXCLUDES.indexOf(key) <= -1
    }
}
