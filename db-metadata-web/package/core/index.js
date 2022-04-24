import utils from '../utils'

const fieldDescs = require.context('./', true, /ui-conf\.(js)$/);
const viewDesc = require.context('../view/', true, /ui-conf\.(js)$/)

const metaBuilder = {};

fieldDescs.keys().forEach(fileName => {
    let item = fieldDescs(fileName);
    if (utils.isFunction(item['callback'])) {
        metaBuilder[item.default['component_name']] = item['callback'];
    }
});
viewDesc.keys().forEach(fileName => {
    let item = viewDesc(fileName);
    if (utils.isFunction(item['callback'])) {
        metaBuilder[item.default['component_name']] = item['callback'];
    }
});

const defaultMeta = {};

fieldDescs.keys().forEach(fileName => {
    let item = fieldDescs(fileName);
    defaultMeta[item.default['component_name']] = item.default;
});
viewDesc.keys().forEach(fileName => {
    let item = viewDesc(fileName);
    defaultMeta[item.default['component_name']] = item.default;
});

const buildDefaultMeta = function (componentName, objectCode, fieldCode) {
    if (metaBuilder.hasOwnProperty(componentName) && utils.isFunction(metaBuilder[componentName])) {
        return metaBuilder[componentName].call(null, objectCode, fieldCode)
    } else {
        return defaultMeta[componentName]
    }
}

export {buildDefaultMeta};
