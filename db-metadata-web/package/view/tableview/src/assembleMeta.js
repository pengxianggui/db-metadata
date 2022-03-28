import utils from '../../../utils'

// init column.showable of columns
const initShowable = function (columns) {
    columns.forEach(item => {
        // TODO 2.2 缓存到sessionStorage
        if (!item.hasOwnProperty('showable')) { // default true: 默认所有字段均展示
            item.showable = true;
        }
    });
};

export default function (mergedMeta) {
    const columnsKey = 'columns';
    if (utils.hasProp(mergedMeta, columnsKey) && utils.isArray(mergedMeta[columnsKey])) {
        initShowable(mergedMeta[columnsKey]);
    }
    return mergedMeta;
}
