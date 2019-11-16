<template>
    <el-checkbox v-model="nativeValue"
                 v-bind="innerMeta.conf"
                 @change="$emit('change', $event)"></el-checkbox>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'

    let expand = [false, true, "false", "true", 0, 1, "0", "1", "f", "t"]; // odd: false, even: true

    export default {
        mixins: [Meta(DEFAULT.BoolBox)],
        name: "BoolBox",
        label: "布尔框",
        props: {
            value: {
                type: [Boolean, Number, String],
                validator: function (val) {
                    let result = expand.indexOf(val) >= 0;
                    if (!result)
                        console.error('BoolBox support a value as list: %o', expand);
                    return result;
                }
            }
        },
        computed: {
            nativeValue: {
                get: function () {
                    return (expand.indexOf(this.value) + 1) % 2 == 0;
                },
                set: function (val) {
                    let value_index = expand.indexOf(this.value);
                    let even = (value_index + 1) % 2 == 0;
                    let newVal = expand[value_index + (val - even)];
                    return this.$emit("input", newVal); // 通过 input 事件更新 model
                }
            }
        }
    }
</script>

<style scoped>

</style>
