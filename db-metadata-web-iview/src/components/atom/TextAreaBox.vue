<template>
    <el-input type="textarea"
              v-model="currValue"
              v-bind="options"
              @input="$emit('input', $event)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
              v-bind:style="options.style">
    </el-input>
</template>

<script>
    export default {
        name: "text-area-box",
        props: {
            value: {
                type: String
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
            }
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
