<template>
    <el-time-picker
            v-model="nativeValue"
            v-bind="innerMeta.conf">
    </el-time-picker>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.TimeBox), Val],
        name: "TimeBox",
        props: {
            value: {
                type: [Date, String]
            },
        },
    }
</script>

<style scoped>

</style>
