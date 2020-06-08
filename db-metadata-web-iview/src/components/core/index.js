const descs = require.context('./', true, /ui-conf\.(js)$/);
const metaDesc = {};

descs.keys().forEach(fileName => {
    let item = descs(fileName);
    metaDesc[item.default['component_name']] = item['ConfDesc'];
});

const defaultMeta = {};

descs.keys().forEach(fileName => {
    let item = descs(fileName);
    defaultMeta[item.default['component_name']] = item.default;
});

export {metaDesc, defaultMeta};
