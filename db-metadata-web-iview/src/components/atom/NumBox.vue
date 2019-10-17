<template>
    <el-input-number v-model="currValue" @change="handleChange"></el-input-number>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "num-box",
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
                type: [Number]
            },
        },
        data () {
            return {
            }
        },
        methods: {
            handleChange: function () {

            },
            getDefaultConf: function() {
                return DEFAULT.NumBox
            },
            initConf: function () {
                this.meta.ui_config = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.$merge(this.meta.ui_config, defaultConf)
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
