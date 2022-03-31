import utils from '../../../utils'

// init column.showable of columns
const initShowable = function (columns) {
    columns.forEach(item => {
        // default true: 默认所有字段均展示
        if (!item.hasOwnProperty('showable') || !utils.isBoolean(item.showable)) {
            item.showable = true;
        }
    })
}

export default function (mergedMeta) {
    const columnsKey = 'columns';
    if (utils.hasProp(mergedMeta, columnsKey) && utils.isArray(mergedMeta[columnsKey])) {
        initShowable(mergedMeta[columnsKey]);
    }
    return mergedMeta;
}
