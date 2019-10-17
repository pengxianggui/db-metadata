<template>
    <el-checkbox v-model="currValue"
                 v-bind="meta.ui_config"
                 @change="$emit('change', $event)"></el-checkbox>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "bool-box",
        props: {
            value: {
                type: Boolean
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
                return DEFAULT.BoolBox
            },
            initConf: function () {
                this.meta.ui_config = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.$merge(this.meta.ui_config, defaultConf)
            }
        },
        created() {
            this.initConf()
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
