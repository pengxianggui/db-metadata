<template>
    <el-input type="textarea"
              v-model="nativeValue"
              v-bind="innerMeta.conf"
              @input="$emit('input', $event)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)">
    </el-input>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.TextAreaBox), Val],
        name: "TextAreaBox",
        label: "文本域",
        props: {
            value: String,
        },
    }
</script>

<style scoped>

</style>
