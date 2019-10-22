<template>
    <el-input v-model="nativeValue"
              v-bind="innerMeta.conf"
              @input="handleInput"
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
                innerMeta: {}
            }
        },
        methods: {
            handleInput: function () {
            },
            getDefaultMeta: function() {
                return DEFAULT.TextBox
            },
            initMeta: function () {
                let defaultMeta = this.getDefaultMeta();
                this.$merge(this.innerMeta, defaultMeta);
                this.$merge(this.innerMeta, this.meta);
            }
        },
        created() {
            this.initMeta()
        },
        computed: {
            nativeValue: {
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
