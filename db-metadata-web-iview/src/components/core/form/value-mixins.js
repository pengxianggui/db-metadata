/**
 * value mixins, for atom component which is Basic input component,
 * you should provide a property named value in props at first!
 */

/**
 * description as above
 * @param format , the param is a function which is used to format the value
 * @returns {{computed: {nativeValue: {set: (function(*=): this), get: computed.nativeValue.get}}}}
 * @constructor
 */
export function Val(format) {
    return {
        computed: {
            nativeValue: {
                get: function () {
                    if (typeof format === 'function')
                        return format.call(this, this.value);
                    console.warn('param format is not a function. it will be ignored!');
                    return this.value;
                },
                set: function (n) {
                    if (n === '') n = null;
                    return this.$emit("input", n); // 通过 input 事件更新 model
                }
            }
        }
    }
}

/**
 * default export, while you needn't format the value
 */
export default {
    computed: {
        nativeValue: {
            get: function () {
                return this.value;
            },
            set: function (n) {
                if (n === '') n = null;
                return this.$emit("input", n); // 通过 input 事件更新 model
            }
        }
    }
}