<template>
    <el-select v-model="nativeValue"
               v-bind="$reverseMerge(innerMeta.conf, $attrs)"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <template v-if="!innerMeta.group">
            <el-option v-for="item in innerOptions" :key="item.value" :label="item.key"
                       :value="item.value">
                {{item.key}}
            </el-option>
        </template>
        <template v-else>
            <el-option-group
                v-for="group in innerOptions"
                :key="group.label"
                :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.key"
                           :value="item.value ? item.value : item">
                    {{item.key}}
                </el-option>
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
                innerOptions: []
            }
        },
        props: {
            value: [Object, String, Number, Array]
        },
        created() {
            console.log(this.$attrs)
        },
        computed: {
            nativeValue: {
                get: function () {
                    let multiple = (this.innerMeta.hasOwnProperty('conf') && this.innerMeta['conf']['multiple'] === true);
                    if (multiple) {
                        switch (utils.typeOf(this.value)) {
                            case "[object String]":
                                return this.value.trim() === '' ? [] : this.value.split(',');
                            case "[object Array]":
                                return this.value;
                            case "[object Undefined]":
                                return [];
                            case "[object Null]":
                                return [];
                        }
                    }
                    return this.value;
                },
                set: function (val) {
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
