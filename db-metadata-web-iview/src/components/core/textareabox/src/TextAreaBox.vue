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
    import {DEFAULT} from '@/constant'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'

    export default {
        mixins: [Meta(DEFAULT.TextAreaBox), Val()],
        name: "TextAreaBox",
        label: "文本域",
        props: {
            value: String,
        },
    }
</script>

<style scoped>

</style>
