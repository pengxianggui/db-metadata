<template>
    <el-date-picker
            v-model="currValue"
            type="date"
            v-bind="meta.ui_config">
    </el-date-picker>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "date-box",
        props: {
            meta: {
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
        methods: {
            getDefaultConf: function() {
                return DEFAULT.DateBox
            },
            initConf: function () {
                this.meta.ui_config = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.merge(this.meta.ui_config, defaultConf)
                if (this.meta.ui_config['value-format'].toLowerCase() === 'date') {
                    delete this.meta.ui_config['value-format']
                }
            }
        },
        created () {
            this.initConf()
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
