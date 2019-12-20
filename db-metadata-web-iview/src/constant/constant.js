/**
 * axios baseURL
 * @type {string}
 */
export const BASE_URL = '/';

/**
 * 统一默认的错误信息（当服务端不给msg时指定）
 * @type {string}
 */
export const ERROR_MSG = '发生错误';

/**
 *统一默认的成功信息（当服务端不给msg时指定）
 * @type {string}
 */
export const SUCCESS_MSG = '操作成功';

/**
 * 分页每页数量可选范围
 * @type {number[]}
 */
export const PAGE_NUM_AREA = [5, 10, 20, 50, 100, 200];

/**
 * 默认主键字段
 * @type {string}
 */
export const DEFAULT_PRIMARY_KEY = 'id';

/**
 * 功能类别列表
 * @type {string[]}
 */
export const FEATURE_TYPE = {
    MasterSlaveGrid: 'MasterSlaveGrid',
    SingleGrid: 'SingleGrid'
};