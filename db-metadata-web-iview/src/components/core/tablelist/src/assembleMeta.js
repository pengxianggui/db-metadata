import utils from '@/utils'

// init column.showable of columns
const initShowable = function (columns) {
    columns.forEach(item => {
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
    const {data_url, delete_url, objectCode} = mergedMeta;

    // 当服务端meta缺少data_url或delete_url时, 会使用系统默认的data_url和delete_url, 系统默认的需要进行编译(objectCode是动态的)
    if (!utils.isEmpty(objectCode)) {
        mergedMeta['data_url'] = utils.compile(data_url, {objectCode: objectCode});
        mergedMeta['delete_url'] = utils.compile(delete_url, {objectCode: objectCode});
    }
    return mergedMeta;
}