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
export const PAGE_NUM_AREA = [1, 10, 20, 50, 100, 200];

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
export const FORM_TO_ADD_URL = "/form/toAdd/{objectCode}";

/**
 * form.doAdd
 * @type {string}
 */
export const FORM_DO_ADD_URL = "/form/toAdd/{objectCode}";

/**
 * form.toUpdate
 * @type {string}
 */
export const FORM_TO_EDIT_URL = "/form/toUpdate/{objectCode}?id={id}";
