<template>
    <el-input v-model="nativeValue"
              v-bind="$reverseMerge(innerMeta.conf, $attrs)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
              @input="$emit('input', $event)"
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
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val()],
        name: "TextBox",
        label: "文本框",
        props: {
            value: [String, Number],
        }
    }
</script>

<style scoped>

</style>
