import {restUrl} from "../constant/url"
import {compile} from "./url";

/**
 * 获取记录新增的form meta
 * @param objectCode
 * @returns {*}
 */
export function getAddFormMeta(axios, instanceCode) {
    const url = compile(restUrl.RECORD_TO_ADD, {instanceCode: instanceCode})
    return axios.get(url)
}

/**
 * 获取记录更新的form meta
 * @param objectCode
 * @param primaryKv 当为联合主键时, 此值为pk1_pv1,pk2_pv2,pk3_pv3； 若为单主键, 则primaryKv为 主键值 value
 * @returns {*}
 */
export function getUpdateFormMeta(axios, instanceCode, primaryKv) {
    const url = compile(restUrl.RECORD_TO_UPDATE, {instanceCode: instanceCode, primaryKv: primaryKv})
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
