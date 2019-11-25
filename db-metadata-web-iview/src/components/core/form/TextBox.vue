<template>
    <el-input v-model="nativeValue"
              v-bind="innerMeta.conf"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
    >
        <template #prefix>
            <slot name="prefix"></slot>
        </template>
        <template #suffix>
            <slot name="suffix"></slot>
        </template>
        <template #prepend>
            <slot name="prepend"></slot>
        </template>
        <template #append>
            <slot name="append"></slot>
        </template>
    </el-input>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.TextBox), Val],
        name: "TextBox",
        label: "文本框",
        props: {
            value: [String, Number],
        }
    }
</script>

<style scoped>

</style>
