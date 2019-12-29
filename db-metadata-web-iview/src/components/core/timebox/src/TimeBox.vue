<template>
    <el-time-picker
            v-model="nativeValue"
            v-bind="$reverseMerge(innerMeta.conf, $attrs)">
    </el-time-picker>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'

    export default {
        mixins: [Meta(DEFAULT.TimeBox), Val()],
        name: "TimeBox",
        label: "时间框",
        props: {
            value: [Date, String],
        },
    }
</script>

<style scoped>

</style>
