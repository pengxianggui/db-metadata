import {isBoolean} from "../../utils/common";
import utils from "../../utils";

export default {
    methods: {
        _showable(row, showConf) {
            if (isBoolean(showConf)) {
                return showConf
            }

            const showCallbackFn = utils.strToFn(showConf);
            return showCallbackFn.call(this, row)
        }
    }
}