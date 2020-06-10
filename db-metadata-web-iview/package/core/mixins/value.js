import utils from '../../utils'

/**
 * value mixins, for atom component which is Basic input component,
 * you should provide a property named value in props at first!
 */

/**
 * description as above
 * @param conver , the param is a function which is used to conver the value
 * @param reverse , the param is a function which is used to reverse the value
 * @returns {{computed: {nativeValue: {set: (function(*=): this), get: computed.nativeValue.get}}}}
 * @constructor
 */
export default function Val(conver, reverse) {
    return {
        computed: {
            nativeValue: {
                get: function () {
                    if (utils.isFunction(conver)) {
                        return conver.call(this, this.value);
                    }
                    return this.value;
                },
                set: function (val) {
                    if (utils.isFunction(reverse)) {
                        const newVal = reverse.call(this, val);
                        this.$emit('input', newVal);
                    }
                    if (val === '') val = null;
                    return this.$emit("input", val); // 通过 input 事件更新 model
                }
            }
        }
    }
}
