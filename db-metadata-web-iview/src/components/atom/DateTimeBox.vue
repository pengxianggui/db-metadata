<template>
    <el-date-picker
            v-model="nativeValue"
            type="datetime"
            v-bind="innerMeta.conf"
            >
    </el-date-picker>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.DateTimeBox), Val],
        name: "DateTimeBox",
        props: {
            value: {
                type: [Date, String]
            }
        },
    }
</script>

<style scoped>

</style>
