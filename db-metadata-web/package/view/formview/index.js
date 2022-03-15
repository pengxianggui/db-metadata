import FormView from './src/FormView'
import FormFieldView from "./src/FormFieldView";
import NestFormItem from "./src/NestFormItem";
import {isEmpty} from "../../utils/common";
import {formTypes} from "./ui-conf";
import {restUrl} from "../../constant/url";
import {compile} from "../../utils/url";

FormView.install = function (Vue) {
    Vue.component(FormView.name, FormView);
    Vue.component(FormFieldView.name, FormFieldView);
    Vue.component(NestFormItem.name, NestFormItem);
};

/**
 * 获取得到表单实例配置的url
 * @param formType
 * @returns {string}
 */
export const getFormInstanceUrl = function (formType, primaryKv) {
    let url = restUrl.VIEW_INSTANCE_CONF // 默认
    let params = {}

    if (!isEmpty(formType)) {
        switch (formType.toUpperCase()) {
            case formTypes.add:
                url = restUrl.RECORD_TO_ADD
                break
            case formTypes.update:
                if (!isEmpty(primaryKv)) {
                    params.primaryKv = primaryKv
                }
                url = compile(restUrl.RECORD_TO_UPDATE, params)
                break
            case formTypes.view:
                if (!isEmpty(primaryKv)) {
                    params.primaryKv = primaryKv
                }
                url = compile(restUrl.RECORD_TO_VIEW, params)
                break
        }
    }

    return url
}


export default FormView;
