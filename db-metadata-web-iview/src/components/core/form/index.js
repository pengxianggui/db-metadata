// import BoolBox from './BoolBox'
// import CheckBox from './CheckBox'
// import DateBox from './DateBox'
// import DateTimeBox from './DateTimeBox'
// import DropDownBox from './DropDownBox'
// import FileBox from './FileBox'
// import FindBox from './FindBox'
// import ImgBox from './ImgBox'
// import JsonBox from './JsonBox'
// import NumBox from './NumBox'
// import PassBox from './PassBox'
// import RadioBox from './RadioBox'
// import TextAreaBox from './TextAreaBox'
// import TextBox from './TextBox'
// import TimeBox from './TimeBox'
//
//
// import upperFirst from "lodash/upperFirst";
// import camelCase from "lodash/camelCase";
// import Vue from "vue/types/vue";



// 注册原子业务组件
const formComponents = require.context('./', true,/\w+\.(vue)$/);

let formComps = formComponents.keys().map(fileName => {
    const formComponent = formComponents(fileName);
    return formComponent.default || formComponent;
});

export default formComps