import {restUrl} from "./constant/url";
import utils from "./utils";

export const elementVersion = '2.12.0';

export const pageNumArea = [5, 10, 20, 50, 100, 200];

export const defaultPrimaryKey = 'id';

/**
 * 功能模板中, 当两个元对象通过另一个中间表进行关联时, 传递主键的key值
 * @type {string}
 */
export const relatedId = '_relate_id'

/**
 * 系统配置
 * @type {{}}
 */
export const appConfig = {}

export const configApp = function (Vue, opts = {}) {
    const {axios} = opts
    if (!axios) {
        console.error('[MetaElement]请配置axios')
        return
    }

    axios.get(restUrl.GET_APP_CONFIG).then(({data}) => {
        utils.reverseMerge(appConfig, data)
    })
}
