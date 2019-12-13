<template>
    <el-checkbox-group v-model="nativeValue">
        <el-checkbox v-for="item in innerOptions"
                     :key="item.key"
                     :label="item.value"
                     v-bind="$reverseMerge(innerMeta.conf, $attrs)">
            {{item.key}}
        </el-checkbox>
    </el-checkbox-group>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT} from '@/constant'
    import Meta from '@/components/core/mixins/meta'
    import {options} from "@/components/core/mixins/methods";
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.CheckBox), Val, options],
        name: "CheckBox",
        label: "复选框",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: [Array, String]
        },
        computed: {
            nativeValue: {
                get: function () {
                    switch (utils.typeOf(this.value)) {
                        case "[object String]":
                            return this.value.trim() === '' ? [] : this.value.split(',');
                        case "[object Undefined]":
                            return [];
                        case "[object Null]":
                            return [];
                    }
                    return this.value;
                },
                set: function (val) {
                    let newVal = val;
                    switch (utils.typeOf(this.value)) {
                        case "[object String]":
                            newVal = val.join(',');
                            break;
                    }
                    this.$emit('input', newVal);
                }
            }
        }
    }
</script>

<style scoped>

</style>
