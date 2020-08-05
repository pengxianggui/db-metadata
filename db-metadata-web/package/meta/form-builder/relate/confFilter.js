import DefaultFormViewMeta from '../../../core/formview/ui-conf'

const FORM_EXCLUDES = ['columns', 'buttons', 'name', 'group', 'objectCode', 'objectPrimaryKey', 'component_name'];
const FIELD_EXCLUDES = ['name', 'group', 'objectCode', 'objectPrimaryKey'];


/**
 * 判断某些控件的配置项是否显示
 * @param componentName
 * @param key
 */
export default function (componentName, key) {
    if (componentName === DefaultFormViewMeta.component_name) {
        return FORM_EXCLUDES.indexOf(key) > -1 ? false : true;
    } else {
        return FIELD_EXCLUDES.indexOf(key) > -1 ? false : true;
    }
}
