import utils from '@/utils'

// init column.showable of columns
const initShowable = function (columns) {
    columns.forEach(item => {
        if (!item.hasOwnProperty('showable')) { // default true
            item.showable = true;
        }
    });
};

export default function (mergedMeta) {
    const columnsKey = 'columns';
    if (utils.hasProp(mergedMeta, columnsKey) && utils.isArray(mergedMeta[columnsKey])) {
        initShowable(mergedMeta[columnsKey]);
    }

    const {"tree-props": treeProps} = this; // 该属性支持独立传入
    utils.reverseMerge(mergedMeta['conf']['tree-props'], treeProps);
    return mergedMeta;
}