<template>
    <el-input
            v-model="formValue"
            :type="options.type"
            :maxlength="options.maxlength"
            :minlength="options.minlength"
            :show-word-limit="options.showWordLimit"
            :placeholder="options.placeholder"
            :clearable="options.clearable"
            :show-password="options.showPassword"
            :disabled="options.disabled"
            :size="options.size"
            :prefix-icon="options.prefixIcon"
            :suffix-icon="options.suffixIcon"
            :rows="options.rows"
            :autosize="options.autosize"
            :readonly="options.readonly"
            :autofocus="options.autofocus"
            :label="options.label"
            :tabindex="options.tabindex"
            :validate-event="options.validateEvent"
            @input.native="$emit('input', formValue)"
            @blur="$emit('blur', $event)"
            @focus="$emit('focus', $event)"
            @change="$emit('change', $event)"
            @clear="$emit('clear', $event)"
    ></el-input>
</template>

<script>
    export default {
        name: "x-input",
        props: {
            value: {
                required: true
            },
            options: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        data () {
            return {
                formValue: null
            }
        },
        watch: {
            value () {
                this.initData()
            }
        },
        methods: {
            initData () {
                this.formValue = this.value
            }
        },
        mounted () {
            this.initData()
            // this.on('initFormItem', () => {
            //     this.initData()
            // })
        }
    }
</script>

<style scoped>

</style>