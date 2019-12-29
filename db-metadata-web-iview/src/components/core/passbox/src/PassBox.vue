<template>
    <el-input v-model="nativeValue"
              v-bind="$reverseMerge(innerMeta.conf, $attrs)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
              type="password"
    ></el-input>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'

    export default {
        mixins: [Meta(DEFAULT.PassBox), Val()],
        name: "PassBox",
        label: "密码框",
        props: {
            value: String,
        }
    }
</script>

<style scoped>

</style>
