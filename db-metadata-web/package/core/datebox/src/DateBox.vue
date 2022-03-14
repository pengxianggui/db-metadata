<template>
    <el-date-picker
            v-model="nativeValue"
            :type="type"
            v-bind="conf">
    </el-date-picker>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'
    import {assertEmpty} from "../../../utils/common";

    export default {
        mixins: [Meta(DefaultMeta), Val()],
        name: "DateBox",
        label: "日期框",
        props: {
            value: {
                type: [Date, String]
            }
        },
        computed: {
            conf() {
                const {innerMeta: {conf}, $attrs} = this
                return this.$reverseMerge(conf, $attrs)
            },
            type() {
              const {innerMeta: {conf: {type:metaType} = {}} = {}, $attrs: {type: attrType}} = this
              return assertEmpty(assertEmpty(attrType, metaType), 'date')
            }
        }
    }
</script>

<style scoped>

</style>
