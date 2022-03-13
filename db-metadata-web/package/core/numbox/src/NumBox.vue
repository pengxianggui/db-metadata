<template>
    <el-input-number v-model="nativeValue"
                     @change="$emit('change', $event)"
                     v-bind="$reverseMerge(meta['conf'], $attrs)"></el-input-number>
</template>

<script>
    import utils from '../../../utils'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    let conver = function (value) {
        if (utils.isEmpty(value)) {
            return 0
        } else {
            return parseFloat(value)
        }
    };

    export default {
        mixins: [Meta(DefaultMeta), Val(conver)],
        name: "NumBox",
        label: "数字框",
        props: {
            value: [Number, String],
        }
    }
</script>

<style scoped>

</style>
