const descs = require.context('./', true, /ui-conf\.(js)$/);
const uiConfDesc = {};

descs.keys().forEach(fileName => {
    let item = descs(fileName);
    uiConfDesc[item.default['component_name']] = item['ConfDesc'];
});

export {uiConfDesc};

const defaultUiConf = {};

descs.keys().forEach(fileName => {
    let item = descs(fileName);
    defaultUiConf[item.default['component_name']] = item.default;
});