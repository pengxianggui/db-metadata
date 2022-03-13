import {restUrl} from "../constant/url"
import {compile} from "./url";

/**
 * 获取记录新增的form meta
 * @param objectCode
 * @returns {*}
 */
export function getAddFormMeta(axios, objectCode) {
    const url = compile(restUrl.RECORD_TO_ADD, {objectCode: objectCode})
    return axios.get(url)
}

/**
 * 获取记录更新的form meta
 * @param objectCode
 * @param primaryKv 当为联合主键时, 此值为pk1_pv1,pk2_pv2,pk3_pv3； 若为单主键, 则primaryKv为 主键值 value
 * @returns {*}
 */
export function getUpdateFormMeta(axios, objectCode, primaryKv) {
    const url = compile(restUrl.RECORD_TO_UPDATE, {objectCode: objectCode, primaryKv: primaryKv})
    return axios.get(url)
}

/**
 * 获取功能配置
 * @param axios
 * @param featureCode
 * @returns {*}
 */
export function loadFeature(axios, featureCode) {
    let url = compile(restUrl.FEATURE_LOAD, {
        featureCode: featureCode
    });
    return axios.safeGet(url);
}

/**
 * 获取单表实例配置
 * @param axios
 * @param objectCode
 * @returns {*}
 */
export function getTableViewMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TableView'
    });
    return axios.safeGet(url);
}

/**
 * 获取树表实例配置
 * @param axios
 * @param objectCode
 * @returns {*}
 */
export function getTableTreeViewMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TableTreeView'
    });
    return axios.safeGet(url)
}

/**
 * 获取树实例配置
 * @param axios
 * @param objectCode
 * @returns {*}
 */
export function getTreeMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TreeView'
    });
    return axios.safeGet(url);
}

/**
 * 获取DataList实例配置
 * @param axios
 * @param objectCode
 * @returns {*}
 */
export function getDataListMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'DataList'
    });
    return axios.safeGet(url);
}

/**
 * 获取SearchView实例配置
 * @param axios
 * @param objectCode
 * @returns {*}
 */
export function getSearchViewMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        componentCode: 'SearchView',
        objectCode: objectCode
    });
    return axios.safeGet(url);
}

/**
 * 通过功能获取功能中包含的实例配置。注意区别于{@link loadFeature}。这个方法应当舍弃。
 * @param axios
 * @param featureCode
 * @returns {*}
 */
export function getMetaFromFeature_TreeTableTmpl(axios, featureCode) {
    let url = compile(restUrl.FEATURE_TREE_AND_TABLE_META, {
        featureCode: featureCode
    })
    return axios.safeGet(url)
}
