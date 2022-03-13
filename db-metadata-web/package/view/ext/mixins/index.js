import {isBoolean} from "../../../utils/common";
import utils from "../../../utils";

/**
 * 视图通用的混入
 * @type {{methods: {getInstanceConf(*=): *}}}
 */
export const ViewMixin = {
    methods: {
        // 是否显示。show可以为函数, 将data传入此函数, 返回值作为此方法返回值
        ifShow(show, data) {
            if (isBoolean(show)) {
                return show
            }
            const showCallbackFn = utils.strToFn(show);
            return showCallbackFn.call(this, data)
        }
    }
}
