/**
 * value mixins, for atom component which is Basic input component,
 * you should provide a property named value in props at first!
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
                    return this.$emit("input", n); // 通过 input 事件更新 model
                }
            }
        }
    }
}

export default {
    computed: {
        nativeValue: {
            get: function () {
                return this.value;
            },
            set: function (n) {
                return this.$emit("input", n); // 通过 input 事件更新 model
            }
        }
    }
}