import Vue from 'vue'
import {restUrl} from "../constant/url"
import {compile} from "./url";

/**
 * 获取记录新增的form meta
 * @param objectCode
 * @returns {*}
 */
export function getAddFormMeta(objectCode) {
    const url = compile(restUrl.RECORD_TO_ADD, {objectCode: objectCode})
    return Vue.prototype.$axios.get(url)
}

/**
 * 获取记录更新的form meta
 * @param objectCode
 * @param primaryKv 当为联合主键时, 此值为pk1_pv1,pk2_pv2,pk3_pv3； 若为单主键, 则primaryKv为 主键值 value
 * @returns {*}
 */
export function getUpdateFormMeta(objectCode, primaryKv) {
    const url = compile(restUrl.RECORD_TO_UPDATE, {objectCode: objectCode, primaryKv: primaryKv})
    return Vue.prototype.$axios.get(url)
}

export function loadFeature(featureCode) {
    let url = compile(restUrl.FEATURE_LOAD, {
        featureCode: featureCode
    });
    return Vue.prototype.$axios.safeGet(url);
}

export function getTableViewMeta(objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TableView'
    });
    return Vue.prototype.$axios.safeGet(url);
}

export function getTableTreeViewMeta(objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'TableTreeView'
    });
    return Vue.prototype.$axios.safeGet(url)
}


export function getTreeMeta(objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'Tree'
    });
    return Vue.prototype.$axios.safeGet(url);
}


export function getDataListMeta(objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        objectCode: objectCode,
        componentCode: 'DataList'
    });
    return Vue.prototype.$axios.safeGet(url);
}


export function getSearchViewMeta(objectCode) {
    let url = compile(restUrl.COMPONENT_INSTANCE_META, {
        componentCode: 'SearchView',
        objectCode: objectCode
    });
    return Vue.prototype.$axios.safeGet(url);
}

// 从功能中直接获取meta
export function getMetaFromFeature_TreeTableTmpl(featureCode) {
    let url = compile("", { // TODO 补充url
        featureCode: featureCode
    })
    return Vue.prototype.$axios.safeGet(url)
}