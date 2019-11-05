<template>
    <el-date-picker
            v-model="nativeValue"
            type="date"
            v-bind="innerMeta.conf">
    </el-date-picker>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.DateBox), Val],
        name: "DateBox",
        props: {
            value: {
                type: [Date, String]
            }
        },
    }
</script>

<style scoped>

</style>
