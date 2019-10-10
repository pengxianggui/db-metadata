<template>
    <el-radio-group v-model="currValue">
        <el-radio v-for="item in metaData.options"
                  :key="item[metaData.value]"
                  :label="item[metaData.value]">
            {{item[metaData.label]}}
        </el-radio>
    </el-radio-group>
</template>

<script>
    export default {
        name: "radio-box",
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
