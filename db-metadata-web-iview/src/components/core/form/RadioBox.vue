<template>
    <el-radio-group v-model="nativeValue">
        <el-radio v-for="item in innerOptions"
                  :key="item.key"
                  :label="item.value"
                  v-bind="innerMeta.conf">
            {{item.key}}
        </el-radio>
    </el-radio-group>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '@/components/core/mixins/meta'
    import {initOptions, getOptions} from "@/components/core/mixins/methods";
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.RadioBox), Val, initOptions, getOptions],
        name: "RadioBox",
        label: "单选框",
        data () {
            return {
                innerOptions: []
            }
        },
        props: {
            value: [Object, String],
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
    }
</script>

<style scoped>

</style>
