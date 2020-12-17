/**
 * Form Field组件的展示形态 TODO 目前视图状态还没有在每个 Form Field Component中实现， 在FormFieldView中枚举去实现形态判断。
 * 后面可以考虑混入每个Form Field Component， 在控件内部显示isView下的形态
 */
export default {
    inject: {
        isView: {
            default: false
        }
    }
}