<template>
    <el-select v-model="nativeValue"
               v-bind="innerMeta.conf"
               @change="$emit('change', $event)"
               @remove-tag="$emit('remove-tag', $event)"
               @clear="$emit('clear', $event)"
               @blur="$emit('blur', $event)"
               @focus="$emit('focus', $event)">
        <template v-if="!innerMeta.group">
            <el-option v-for="item in innerOptions" :key="item.value" :label="item.key"
                       :value="item.value">
                {{item.key}}
            </el-option>
        </template>
        <template v-else>
            <el-option-group
                v-for="group in innerOptions"
                :key="group.label"
                :label="group.label">
                <el-option v-for="item in group.options" :key="item.value" :label="item.key"
                           :value="item.value ? item.value : item">
                    {{item.key}}
                </el-option>
            </el-option-group>
        </template>
    </el-select>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import utils from '@/utils'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.DropDownBox), Val],
        name: "DropDownBox",
        label: "下拉框",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: {
                type: [Object, String, Number, Array]
            },
            options: Array,
        },
        methods: {
            getOptions: function () {
                // http request options data by innerMeta.data_url
                let url = this.innerMeta['data_url'];
                if (url) {
                    this.$axios.safeGet(url).then(resp => {
                        // if provide format callback fn, execute callback fn
                        let format = this.getBehavior('format');
                        this.innerOptions = format ? format(resp.data) : resp.data;
                        this.$emit('update:options', this.innerOptions);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                }
            },
            initOptions: function () {
                let options = this.options;
                if (options !== undefined) { // 父组件定义了options
                    this.innerOptions = options;
                    return;
                }
                if (this.innerMeta.hasOwnProperty('options')
                    && Array.isArray(this.meta['options'])
                    && this.meta['options'].length > 0) { // 组件元对象定义了options, 并且有值
                    this.innerOptions = this.innerMeta['options'];
                    return;
                }
                if (this.innerMeta.hasOwnProperty('data_url')) {
                    this.getOptions();
                    return
                }
                console.error("options or data_url in meta provide one at least!")
            },
        },
        watch: {
            'innerMeta.data_url': function () {
                this.initOptions();
            },
            'innerMeta.options': function () {
                this.initOptions();
            }
        },
        created() {
        },
        mounted() {
            // init option data
            this.initOptions()
        },
        computed: {
            nativeValue: {
                get: function () {
                    let multiple = this.innerMeta.hasOwnProperty('conf') && this.innerMeta['conf']['multiple'] === true;
                    if (multiple) {
                        switch (utils.typeOf(this.value)) {
                            case "[object String]":
                                return this.value.trim() === '' ? [] : this.value.split(',');
                            case "[object Array]":
                                return this.value;
                        }
                    }
                    return this.value;
                },
                set: function (val) {
                    let newVal = val;
                    let multiple = this.innerMeta.hasOwnProperty('conf') && this.innerMeta['conf']['multiple'] === true;
                    if (multiple) {
                        switch (utils.typeOf(this.value)) {
                            case "[object String]":
                                newVal = val.join(',');
                                break;
                            case "[object Array]":
                                newVal = val;
                                break;
                        }
                    }
                    this.$emit('input', newVal);
                }
            }
        }
    }
</script>

<style scoped>

</style>
