<template>
    <el-date-picker
            v-model="currValue"
            type="datetime"
            v-bind="metaData.ui_config"
            >
    </el-date-picker>
</template>

<script>
    export default {
        name: "date-time-box",
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
                type: [Date, String]
            }
        },
        data () {
            return {}
        },
        created () {
            if (!this.metaData.ui_config['value-format'] || this.metaData.ui_config['value-format'] !== 'Date') {
                this.metaData.ui_config['value-format'] = 'yyyy-MM-dd HH:mm:ss'
            } else {
                delete this.metaData.ui_config['value-format']
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
