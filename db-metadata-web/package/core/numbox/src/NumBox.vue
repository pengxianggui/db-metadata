<template>
    <el-input-number v-model="nativeValue"
                     @change="$emit('change', $event)"
                     v-bind="$reverseMerge(innerMeta['conf'], $attrs)"></el-input-number>
</template>

<script>
    import utils from '../../../utils'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    let conver = function (value) {
        if (utils.isEmpty(value)) {
            return undefined // 只有undefined, elementUI的 el-input-number才能设置为空，否则总是0
        } else if (utils.isNumber(value)){
            return value
        } else {
            return parseFloat(utils.convertToString(value))
        }
    };

    let reverse = function (value) {
        if (utils.isEmpty(value)) {
            return undefined;
        }
        return value;
    }

    export default {
        mixins: [Meta(DefaultMeta), Val(conver, reverse)],
        name: "NumBox",
        label: "数字框",
        props: {
            value: [Number, String],
        }
    }
</script>

<style scoped>

</style>
