<template>
    <el-date-picker
            v-model="nativeValue"
            type="date"
            v-bind="$reverseMerge(innerMeta.conf, $attrs)">
    </el-date-picker>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.DateBox), Val],
        name: "DateBox",
        label: "日期框",
        props: {
            value: {
                type: [Date, String]
            }
        },
    }
</script>

<style scoped>

</style>
