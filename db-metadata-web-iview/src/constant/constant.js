/**
 * axios baseURL
 * @type {string}
 */
export const BASE_URL = '/';

/**
 * 分页每页数量可选范围
 * @type {number[]}
 */
export const PAGE_NUM_AREA = [10, 20, 50, 100, 200];

/**
 * 上传路径
 * @type {string}
 */
export const UPLOAD_PATH= "";

/**
 * 选项数据获取路径
 * @type {string}
 */
export const OPTION_PATH= "";

/**
 * 删除接口
 * @type {string}
 */
export const TABLE_DATA_DELETE_URL = "/table/delete?objectCode={objectCode}&ids={ids}";

/**
 * form.toAdd
 * @type {string}
 */
export const FORM_META_ADD_URL = "/form/toAdd/{objectCode}";

/**
 * form.toUpdate
 * @type {string}
 */
export const FORM_META_EDIT_URL = "/form/toAdd/{objectCode}?id={id}";
