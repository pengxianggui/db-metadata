<template>
    <el-input-number v-model="nativeValue"
                     @change="$emit('change', $event)"
                     v-bind="$reverseMerge(innerMeta['conf'], $attrs)"></el-input-number>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT} from '@/constant'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'

    let conver = function (value) {
        return utils.isEmpty(value) ? 0 : value;
    };

    export default {
        mixins: [Meta(DEFAULT.NumBox), Val(conver)],
        name: "NumBox",
        label: "数字框",
        props: {
            value: Number,
        }
    }
</script>

<style scoped>

</style>
