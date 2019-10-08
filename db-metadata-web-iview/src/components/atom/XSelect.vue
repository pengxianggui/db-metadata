<template>
    <el-select v-model="formValue"
               :multiple="options.multiple"
               :disabled="options.disabled"
               :value-key="options.valueKey"
               :size="options.size"
               :clearable="options.clearable"
               :collapse-tags="options.collapseTags"
               :multiple-limit="options.multipleLimit"
               :name="options.name"
               :autocomplete="options.autocomplete"
               :placeholder="options.placeholder"
               :filterable="options.filterable"
               :allow-create="options.allowCreate"
               :filter-method="options.filterMethod"
               :remote="options.remote"
               :remote-method="options.remoteMethod"
               :loading="options.loading"
               :loading-text="options.loadingText"
               :no-match-text="options.noMatchText"
               :no-data-text="options.noDataText"
               :popper-class="options.popperClass"
               :reserve-keyword="options.reserveKeyword"
               :default-first-option="options.defaultFirstOption"
               :popper-append-to-body="options.popperAppendToBody"
               :automatic-dropdown="options.automaticDropdown"
               @input="$emit('input', formValue)"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <el-option v-for="item in options.selectItems" :key="getLabel(item)" :label="getLabel(item)"
                   :value="getValue(item)" :disabled="item.disabled">{{getLabel(item)}}</el-option>
    </el-select>
</template>

<script>
    export default {
        name: "x-select",
        data() {
            return {
                formValue: null
            }
        },
        methods: {
            initData () {
                this.formValue = this.value
            },
            getLabel (item) {
                if (this.options.selectLabelKey) {
                    return item[this.options.selectLabelKey]
                }
                return item.label
            },
            getValue (item) {
                if (this.options.selectValueKey) {
                    return item[this.options.selectValueKey]
                }
                return item.value
            }
        },
        mounted() {
            this.initData()
        },
        watch: {
            value () {
                this.initData()
            }
        },
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
        }
    }
</script>

<style scoped>

</style>
