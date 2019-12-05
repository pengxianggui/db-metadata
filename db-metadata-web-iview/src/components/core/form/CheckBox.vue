<template>
    <el-checkbox-group v-model="nativeValue">
        <el-checkbox v-for="item in innerOptions"
                     :key="item.key"
                     :label="item.value">
            {{item.key}}
        </el-checkbox>
    </el-checkbox-group>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.CheckBox), Val],
        name: "CheckBox",
        label: "复选框",
        data() {
            return {
                innerOptions: []
            }
        },
        props: {
            value: [Array, String],
            options: Array,
        },
        methods: {
            getOptions: function () {
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
                if (this.options !== undefined) { // 父组件定义了options
                    this.innerOptions = this.options;
                    return;
                }
                if (this.meta.hasOwnProperty('options')
                    && Array.isArray(this.meta['options'])
                    && this.meta['options'].length > 0) { // 组件元对象定义了options, 并且有值
                    this.innerOptions = this.innerMeta['options'];
                    return;
                }
                if (this.meta.hasOwnProperty('data_url')) {
                    this.getOptions();
                    return
                }
                console.error("options or data_url in meta provide one at least!")
            },
        },
        watch: {
            'innerMeta.data_url': function () {
                this.getOptions();
            },
            'innerMeta.options': function () {
                this.initOptions();
            }
        },
        mounted() {
            this.initOptions();
        },
        computed: {
            nativeValue: {
                get: function () {
                    switch (utils.typeOf(this.value)) {
                        case "[object String]":
                            return this.value.trim() === '' ? [] : this.value.split(',');
                        case "[object Undefined]":
                            return [];
                        case "[object Null]":
                            return [];
                    }
                    return this.value;
                },
                set: function (val) {
                    let newVal = val;
                    switch (utils.typeOf(this.value)) {
                        case "[object String]":
                            newVal = val.join(',');
                            break;
                    }
                    this.$emit('input', newVal);
                }
            }
        }
    }
</script>

<style scoped>

</style>
