import {CONSTANT} from "../constant";

/**
 * 请求正常下响应数据的统一格式处理（msg保证）
 * @param res
 * @returns {boolean} true表示 state='ok', false 表示state='fail'
 */
export function s_format(res) {
    const fail = res.data && res.data.state !== 'ok';
    const defaultMsg = fail ? CONSTANT.ERROR_MSG : CONSTANT.SUCCESS_MSG;
    res.data.msg = res.data.msg || defaultMsg;
    return !fail;
}

/**
 * 请求失败下响应数据的统一格式处理（msg保证）
 * @param err
 */
export function e_format(err) {
    err.msg = err.toString();
}