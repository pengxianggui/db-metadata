<template>
    <el-input v-model="currValue"
              v-bind="meta.conf"
              @input="$emit('input', $event)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
    ></el-input>
</template>

<script>
    import {DEFAULT} from '@/constant'
    export default {
        name: "text-box",
        props: {
            value: {
                type: String
            },
            /*
             * component meta.
             */
            meta: {
                type: Object,
                default: function () {
                    return {
                    }
                }
            },
        },
        data () {
            return {
            }
        },
        methods: {
            getDefaultMeta: function() {
                return DEFAULT.TextBox
            },
            initMeta: function () {
                this.meta.conf = this.meta.conf || {}
                let defaultMeta = this.getDefaultMeta() || {}
                this.merge(this.meta, defaultMeta)
            }
        },
        created() {
            this.initMeta()
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
