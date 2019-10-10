<template>
    <el-checkbox-group v-model="currValue">
        <el-checkbox v-for="item in metaData.options"
                     :key="item[metaData.value]"
                     :label="item[metaData.value]">
            {{item[metaData.label]}}
        </el-checkbox>
    </el-checkbox-group>
</template>

<script>
    export default {
        name: "check-box",
        data () {
            return {}
        },
        props: {
            value: {
                type: [Array]
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
