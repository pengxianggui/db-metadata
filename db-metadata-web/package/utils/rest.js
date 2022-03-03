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

export function loadFeature(axios, featureCode) {
    let url = compile(restUrl.FEATURE_LOAD, {
        featureCode: featureCode
    });
    return axios.safeGet(url);
}

export function getTableViewMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TableView'
    });
    return axios.safeGet(url);
}

export function getTableTreeViewMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TableTreeView'
    });
    return axios.safeGet(url)
}


export function getTreeMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TreeView'
    });
    return axios.safeGet(url);
}


export function getDataListMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'DataList'
    });
    return axios.safeGet(url);
}


export function getSearchViewMeta(axios, objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        componentCode: 'SearchView',
        objectCode: objectCode
    });
    return axios.safeGet(url);
}

// 从功能中直接获取meta
export function getMetaFromFeature_TreeTableTmpl(axios, featureCode) {
    let url = compile(restUrl.FEATURE_TREE_AND_TABLE_META, {
        featureCode: featureCode
    })
    return axios.safeGet(url)
}
