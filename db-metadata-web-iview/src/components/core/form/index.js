// // 注册表单控件, 方便FormBuilder -> ComponentList 中批量注入
// const formComponents = require.context('./', true,/\w+\.(vue)$/);
//
// let formComps = formComponents.keys().map(fileName => {
//     const formComponent = formComponents(fileName);
//     return formComponent.default || formComponent;
// });
//
// export default formComps