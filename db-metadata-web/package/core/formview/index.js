import FormView from './src/FormView'
import FormFieldView from "./src/FormFieldView";
import NestFormItem from "./src/NestFormItem";

FormView.install = function (Vue) {
    Vue.component(FormView.name, FormView);
    Vue.component(FormFieldView.name, FormFieldView);
    Vue.component(NestFormItem.name, NestFormItem);
};

export default FormView;
