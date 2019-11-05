<template>
    <el-input v-model="nativeValue"
              v-bind="innerMeta.conf"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
    ></el-input>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.TextBox), Val],
        name: "TextBox",
        props: {
            value: String,
        }
    }
</script>

<style scoped>

</style>
