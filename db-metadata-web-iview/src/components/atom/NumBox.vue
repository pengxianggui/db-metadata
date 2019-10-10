<template>
    <el-input-number v-model="currValue" @change="handleChange"></el-input-number>
</template>

<script>
    export default {
        name: "num-box",
        props: {
            metaData: {
                type: Object,
                default: function () {
                    return {
                        ui_config: {}
                    }
                }
            },
            value: {
                type: [Number]
            },
        },
        data () {
            return {
            }
        },
        methods: {
            handleChange: function () {

            }
        },
        computed: {
            currValue: {
                get: function () {
                    return this.value
                },
                set: function (newValue) {
                    this.$emit("input", newValue)
                }
            }
        }
    }
</script>

<style scoped>

</style>
