<template>
    <el-input v-model="formValue"
              v-bind="options"
              @input.native="$emit('input', formValue)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
              v-bind:style="options.style"
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
