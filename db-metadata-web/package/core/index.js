const fieldDescs = require.context('./', true, /ui-conf\.(js)$/);
const viewDesc = require.context('../view/', true, /ui-conf\.(js)$/)

const metaDesc = {};

fieldDescs.keys().forEach(fileName => {
    let item = fieldDescs(fileName);
    metaDesc[item.default['component_name']] = item['ConfDesc'];
});
viewDesc.keys().forEach(fileName => {
    let item = viewDesc(fileName);
    metaDesc[item.default['component_name']] = item['ConfDesc'];
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

export {metaDesc, defaultMeta};
