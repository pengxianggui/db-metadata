import FormView from './src/FormView'

FormView.install = function (Vue) {
    Vue.component(FormView.name, FormView);
};

export default FormView;