<template>
    <el-input-number v-model="nativeValue" @change="$emit('change', $event)" v-bind="innerMeta['conf']"></el-input-number>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.NumBox), Val],
        name: "NumBox",
        props: {
            value: {
                type: [Number]
            },
        }
    }
</script>

<style scoped>

</style>
