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
    import Meta from '@/components/core/mixins/meta'
    import {initOptions, getOptions} from "@/components/core/mixins/methods";
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.CheckBox), Val, initOptions, getOptions],
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
