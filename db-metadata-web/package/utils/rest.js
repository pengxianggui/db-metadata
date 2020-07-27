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
 * @param primaryValue
 * @returns {*}
 */
export function getUpdateFormMeta(objectCode, primaryValue) {
    const url = compile(restUrl.RECORD_TO_UPDATE, {objectCode: objectCode, primaryKv: primaryValue})
    return Vue.prototype.$axios.get(url)
}