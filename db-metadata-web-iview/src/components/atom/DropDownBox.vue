<template>
    <el-select v-model="currValue"
               v-bind="metaData.ui_config"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <el-option v-for="item in metaData.options" :key="item[metaData.value]" :label="item[metaData.label]"
            :value="item[metaData.value] ? item[metaData.value] : item">
            {{item[metaData.value]}}-{{item[metaData.label]}}
        </el-option>
    </el-select>
</template>

<script>
    export default {
        name: "drop-down-box",
        data () {
            return {}
        },
        props: {
            value: {
                type: [Object, String]
            },
            metaData: {
                type: Object,
                default: function () {
                    return {
                        ui_config: {}
                    }
                }
            }
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
