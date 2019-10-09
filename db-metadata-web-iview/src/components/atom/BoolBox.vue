<template>
    <el-switch v-model="currValue"
               :options="options"
               @change="$emit('change', $event)"></el-switch>
</template>

<script>
    export default {
        name: "bool-box",
        props: {
            value: {
                type: Boolean
            },
            options: {
                type: Object,
                default: function () {
                    return {}
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
