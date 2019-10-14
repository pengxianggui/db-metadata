<template>
    <el-select v-model="currValue"
               v-bind="meta.ui_config"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <el-option v-for="item in meta.options" :key="item[meta.value]" :label="item[meta.label]"
            :value="item[meta.value] ? item[meta.value] : item">
            {{item[meta.value]}}-{{item[meta.label]}}
        </el-option>
    </el-select>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "drop-down-box",
        data () {
            return {}
        },
        props: {
            value: {
                type: [Object, String]
            },
            meta: {
                type: Object,
                default: function () {
                    return {
                        ui_config: {}
                    }
                }
            }
        },
        methods: {
            getDefaultConf: function() {
                return DEFAULT.DropDownBox
            },
            initConf: function () {
                this.meta.ui_config = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.merge(this.meta.ui_config, defaultConf)
            }
        },
        created() {
            this.initConf()
        },
        computed: {
            currValue: {
                get: function () {
                    return this.value
                },
                set: function (newValue) {
                    return this.$emit("input", newValue); // 通过 input 事件更新 model
                }
            }
        }
    }
</script>

<style scoped>

</style>
