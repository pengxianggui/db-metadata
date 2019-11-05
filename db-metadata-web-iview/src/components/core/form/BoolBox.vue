<template>
    <el-checkbox v-model="nativeValue"
                 v-bind="innerMeta.conf"
                 @change="$emit('change', $event)"></el-checkbox>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.BoolBox), Val],
        name: "BoolBox",
        props: {
            value: {
                type: Boolean
            }
        },
    }
</script>

<style scoped>

</style>
