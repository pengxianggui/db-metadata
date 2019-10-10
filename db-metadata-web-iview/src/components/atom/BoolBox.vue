<template>
    <el-switch v-model="currValue"
               :options="metaData.ui_config"
               @change="$emit('change', $event)"></el-switch>
</template>

<script>
    export default {
        name: "bool-box",
        props: {
            value: {
                type: Boolean
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
                get: function() {
                    return this.value;
                },
                set: function(newValue) {
                    this.$emit("input", newValue); // 通过 input 事件更新 model
                }
            }
        }
    }
</script>

<style scoped>

</style>
