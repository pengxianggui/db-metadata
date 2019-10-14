<template>
    <el-checkbox-group v-model="currValue">
        <el-checkbox v-for="item in meta.options"
                     :key="item[meta.value]"
                     :label="item[meta.value]">
            {{item[meta.label]}}
        </el-checkbox>
    </el-checkbox-group>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "check-box",
        data () {
            return {}
        },
        props: {
            value: {
                type: [Array]
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
                return DEFAULT.CheckBox
            },
            initConf: function () {
                this.meta.ui_config = this.meta.ui_config || {}
                let defaultConf = this.getDefaultConf() || {}
                this.merge(this.meta.ui_config, defaultConf)
            }
        },
        created() {
            this.initConf()
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
