<template>
    <el-date-picker
            v-model="nativeValue"
            :type="type"
            v-bind="$reverseMerge(innerMeta.conf, $attrs)">
    </el-date-picker>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'
    import {assertEmpty} from "../../../utils/common";

    export default {
        mixins: [Meta(DefaultMeta), Val()],
        name: "DateTimeBox",
        label: "日期时间框",
        props: {
            value: {
                type: [Date, String]
            }
        },
        computed: {
          type() {
            const {innerMeta: {conf: {type:metaType} = {}} = {}, $attrs: {type: attrType}} = this
            return assertEmpty(assertEmpty(attrType, metaType), 'datetime')
          }
        }
    }
</script>

<style scoped>

</style>
