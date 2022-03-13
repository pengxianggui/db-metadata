<template>
    <el-input v-model="nativeValue"
              v-bind="$reverseMerge(meta.conf, $attrs)"
              @blur="$emit('blur', $event)"
              @focus="$emit('focus', $event)"
              @change="$emit('change', $event)"
              @clear="$emit('clear', $event)"
              type="password"
    ></el-input>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val()],
        name: "PassBox",
        label: "密码框",
        props: {
            value: String,
        }
    }
</script>

<style scoped>

</style>
