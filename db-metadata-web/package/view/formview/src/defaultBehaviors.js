import BehaviorMixin from '@/../package/core/mixins/behaviorMixin'

/**
 * 绑定FormView的默认cancel逻辑
 * @type {{mixins: {render, props}[], created(): void, name: string}}
 */
const Cancel = {
    name: 'Cancel',
    mixins: [BehaviorMixin],
    created() {
        const {on, actions: {onCancel}} = this;
        on('cancel', (ev) => {
            onCancel(ev);   // FormView 默认的 取消 行为
        });
    }
};

/**
 * 绑定FormView默认提交逻辑
 * @type {{mixins: {render, props}[], created(): void, name: string}}
 */
const Submit = {
    name: 'Submit',
    mixins: [BehaviorMixin],
    created() {
        const {on, actions: {onSubmit}} = this;
        on('submit', (ev) => {
            onSubmit(ev);   // FormView 默认的 提交 行为
        })
    }
};

export default {
    Cancel,
    Submit
};
