import utils from '../../utils'
import EleProps from '../../constant/element-props'

/*
 * extract config from source map configs. Internal conversion for string to object. [field: conf]
 */
/**
 * ui配置数据提取函数。
 * @param configMap 例如: {
    "instanceCode": "change_log.FormView",
    "fieldsMap": {
      "id": "{\"conf\": {\"rules\": [{\"type\": \"string\", \"message\": \"[主键]是必须填写的\", \"trigger\": [\"blur\"], \"required\": true}], \"maxlength\": 32}, \"name\": \"id\", \"label\": \"主键\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextBox\"}",
      "object_code": "{\"conf\": {\"maxlength\": 64}, \"name\": \"object_code\", \"label\": \"元对象\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextBox\"}",
      "table_name": "{\"conf\": {\"maxlength\": 64}, \"name\": \"table_name\", \"label\": \"表名\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextBox\"}",
      "pkey": "{\"conf\": {\"maxlength\": 32}, \"name\": \"pkey\", \"label\": \"主键字段\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextBox\"}",
      "pvalue": "{\"conf\": {\"maxlength\": 32}, \"name\": \"pvalue\", \"label\": \"主键值\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextBox\"}",
      "action": "{\"conf\": {\"maxlength\": 32}, \"name\": \"action\", \"label\": \"动作\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextBox\"}",
      "olddata": "{\"conf\": {\"maxlength\": 0, \"show-overflow-tooltip\": true}, \"name\": \"olddata\", \"label\": \"旧数据\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"JsonBox\"}",
      "newdata": "{\"conf\": {\"maxlength\": 0, \"show-overflow-tooltip\": true}, \"name\": \"newdata\", \"label\": \"新数据\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"JsonBox\"}",
      "diff": "{\"conf\": {\"resize\": \"none\", \"maxlength\": 65535, \"show-overflow-tooltip\": true}, \"name\": \"diff\", \"label\": \"差异\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextAreaBox\"}",
      "created_by": "{\"conf\": {\"maxlength\": 64}, \"name\": \"created_by\", \"label\": \"创建人\", \"inline\": true, \"default_value\": \"\", \"component_name\": \"TextBox\"}",
      "created_time": "{\"conf\": {\"clearable\": true, \"value-format\": \"yyyy-MM-dd HH:mm:ss\"}, \"name\": \"created_time\", \"label\": \"创建时间\", \"inline\": true, \"default_value\": \"\", \"component_name\": \"DateTimeBox\"}",
      "remark": "{\"conf\": {\"maxlength\": 32}, \"name\": \"remark\", \"label\": \"备注\", \"inline\": false, \"default_value\": \"\", \"component_name\": \"TextBox\"}"
    },
    "instanceName": "自动计算配置",
    "change_log": "{\"btns\": {\"cancel\": {\"conf\": {}, \"label\": \"取消\"}, \"submit\": {\"conf\": {\"type\": \"primary\"}, \"label\": \"提交\"}}, \"conf\": {\"size\": \"medium\", \"rules\": {}, \"inline\": false, \"disabled\": false, \"label-width\": \"100px\"}, \"name\": \"FormView\", \"label\": \"表单模板\", \"columns\": [], \"component_name\": \"FormView\"}"
  }
 * @param key 例如: change_log
 * @param mergeDefault 是否需要合并组件默认的ui配置, 默认为true
 * @returns 例如: {"btns":{"cancel":{"conf":{},"label":"取消"},"submit":{"conf":{"type":"primary"},"label":"提交"}},"conf":{"size":"medium","rules":{},"inline":false,"disabled":false,"label-width":"100px"},"name":"FormView","label":"表单模板","columns":[],"component_name":"FormView"}
 * 注意:
 * 1. 返回值为json对象
 * 2. 并且会将其中的conf字段转为json对象
 * 3. 其中的conf字段会根据组件名合并该组件默认的配置
 */
export default function (configMap, key, mergeDefault = true) {
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
    config['conf'] = utils.strToObject(config['conf']);
    if (mergeDefault) {
        this.$merge(config['conf'], EleProps(config['component_name']));
    }
    return config;
}
