<template>
    <el-date-picker
            v-model="nativeValue"
            type="datetime"
            v-bind="$reverseMerge(innerMeta.conf, $attrs)">
    </el-date-picker>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'

    export default {
        mixins: [Meta(DEFAULT.DateTimeBox), Val()],
        name: "DateTimeBox",
        label: "日期时间框",
        props: {
            value: {
                type: [Date, String]
            }
        },
    }
</script>

<style scoped>

</style>
