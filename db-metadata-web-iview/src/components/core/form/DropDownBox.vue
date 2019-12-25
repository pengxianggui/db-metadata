<template>
    <el-select v-model="nativeValue"
               v-bind="$reverseMerge(innerMeta.conf, $attrs)"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <template v-if="!innerMeta.group">
            <slot name="options" v-bind:options="innerOptions">
                <el-option v-for="item in innerOptions" :key="item.value" :label="item.key"
                           :value="item.value">
                    <slot name="label" v-bind:option="item">
                        {{item.key}}
                    </slot>
                </el-option>
            </slot>
        </template>
        <template v-else>
            <el-option-group
                v-for="group in innerOptions"
                :key="group.label"
                :label="group.label">
                <slot name="options" v-bind:options="innerOptions">
                    <el-option v-for="item in group.options" :key="item.value" :label="item.key"
                               :value="item.value ? item.value : item">
                        <slot name="label" v-bind:options="item">
                            {{item.key}}
                        </slot>
                    </el-option>
                </slot>
            </el-option-group>
        </template>
    </el-select>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT} from '@/constant'
    import Meta from '@/components/core/mixins/meta'
    import {options} from "@/components/core/mixins/methods";
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.DropDownBox), Val, options],
        name: "DropDownBox",
        label: "下拉框",
        inheritAttrs: true,
        data() {
            return {
                eleType: 'string', // default, string|number
                innerOptions: []
            }
        },
        props: {
            value: {
                type: [Object, String, Number, Array],
                validator: function (val) {
                    if (utils.isArray(val)) {
                        return val.every(ele => utils.isString(ele)) || val.every(ele => utils.isNumber(ele));
                    }
                    return true;
                }
            },
        },
        methods: {
            // arrayConver(value, multiple) {
            //     if (multiple) {
            //         return value.map(ele => utils.convertToString(ele));
            //     }
            //     return utils.convertToString(value);
            // },
            // arrayReverse(nativeVal, multiple) {
            //     const {eleType} = this;
            //     if (multiple) {
            //         let fn = eleType === 'string' ? utils.convertToString : utils.convertToNumber;
            //         return nativeVal.map(ele => fn(ele)).join(',');
            //     }
            //     return utils.convertToArray(nativeVal).join(',');
            // },
            // numberConver(value, multiple) {
            //     value = utils.convertToString(value);
            //     return multiple ? [value] : value;
            // },
            // numberReverse(value, multiple) {
            //     const {eleType} = this;
            //
            // }
        },
        computed: {
            nativeValue: {
                get: function () {  // nativeValue 全部转换为string 或 string 数组
                    let multiple = (this.innerMeta.hasOwnProperty('conf') && this.innerMeta['conf']['multiple'] === true);
                    if (multiple) {
                        switch (utils.typeOf(this.value)) {
                            case "[object String]":
                                return this.value.trim() === '' ? [] : this.value.split(',');
                            case "[object Array]":
                                return this.value.map(ele => utils.convertToString(ele));
                            case "[object Undefined]":
                                return [];
                            case "[object Null]":
                                return [];
                        }
                    }
                    return utils.convertToString(this.value);
                },
                set: function (val) {   // nativeValue按照传入的value类型做类型还原
                    let newVal = val;
                    let multiple = (this.innerMeta.hasOwnProperty('conf') && this.innerMeta['conf']['multiple'] === true);
                    if (multiple) {
                        switch (utils.typeOf(this.value)) {
                            case "[object String]":
                                newVal = val.join(',');
                                break;
                            case "[object Array]":
                                newVal = val;
                                break;
                            case "[object Number]":
                                newVal = utils.convertToNumber(val);
                                break;
                        }
                    }
                    this.$emit('input', newVal);
                }
            }
        }
    }
</script>

<style scoped>

</style>
