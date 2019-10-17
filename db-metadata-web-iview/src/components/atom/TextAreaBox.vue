<template>
    <el-input type="textarea"
              v-model="currValue"
              v-bind="meta.ui_config"
              @input="$emit('input', $event)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)">
    </el-input>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "text-area-box",
        props: {
            value: {
                type: String
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
        data () {
            return {
            }
        },
        methods: {
            getDefaultConf: function() {
                return DEFAULT.TextAreaBox
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
