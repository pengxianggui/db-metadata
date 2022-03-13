<template>
    <el-time-picker
            v-model="nativeValue"
            v-bind="conf">
    </el-time-picker>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val()],
        name: "TimeBox",
        label: "时间框",
        props: {
            value: [Date, String],
        },
        computed: {
            conf() {
                const {meta: {conf}, $attrs} = this
                return this.$reverseMerge(conf, $attrs)
            }
        }
    }
</script>

<style scoped>

</style>
