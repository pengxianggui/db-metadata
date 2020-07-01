<template>
    <el-input type="textarea"
              v-model="nativeValue"
              v-bind="$reverseMerge(innerMeta.conf, $attrs)"
              @input="$emit('input', $event)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)">
    </el-input>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val()],
        name: "TextAreaBox",
        label: "文本域",
        props: {
            value: String,
        },
    }
</script>

<style scoped>

</style>
