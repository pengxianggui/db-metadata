<template>
    <el-input v-model="currValue"
              v-bind="metaData.ui_config"
              @input="$emit('input', $event)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
    ></el-input>
</template>

<script>
    export default {
        name: "text-box",
        props: {
            value: {
                type: String
            },
            metaData: {
              type: Object
            },
            // options: {
            //     type: Object,
            //     default: function () {
            //         return {}
            //     }
            // }
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
