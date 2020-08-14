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