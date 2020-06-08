<template>
    <el-date-picker
            v-model="nativeValue"
            type="datetime"
            v-bind="$reverseMerge(innerMeta.conf, $attrs)">
    </el-date-picker>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val()],
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
