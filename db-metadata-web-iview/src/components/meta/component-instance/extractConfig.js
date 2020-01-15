import utils from '@/utils'
import EleProps from '@/config/element-props'

/*
 * extract config from source map configs. Internal conversion for string to object. [field: conf]
 */
export default function (configMap, key) {
    let config, configStr;
    try {
        if (utils.isObject(configMap[key])) {
            config = configMap[key];
        } else {
            configStr = utils.convertToString(configMap[key]);
            config = JSON.parse(configStr);
        }
    } catch (e) {
        config = {};
        console.error("The string can't be parsed by JSON: o%", configStr);
        console.error(e);
    }
    config['conf'] = utils.convertToObject(config['conf']);
    this.$merge(config['conf'], EleProps(config['component_name']));
    return config;
};