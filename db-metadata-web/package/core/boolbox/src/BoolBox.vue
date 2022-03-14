<template>
    <el-checkbox v-model="nativeValue"
                 v-bind="$reverseMerge(innerMeta.conf, $attrs)"
                 @change="$emit('change', $event)">
      <slot></slot>
    </el-checkbox>
</template>

<script>
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import conver from './conver'
    import reverse from './reverse'
    import expand from './expand'
    import DefaultMeta from '../ui-conf'

    export default {
        mixins: [Meta(DefaultMeta), Val(conver, reverse)],
        name: "BoolBox",
        label: "布尔框",
        props: {
            value: {
                type: [Boolean, Number, String],
                validator: function (val) {
                    let result = expand.indexOf(val) >= 0;
                    if (!result) console.warn('BoolBox support a value as list: %o. but get: %o', expand, val);
                    return result;
                }
            }
        }
    }
</script>

<style scoped>

</style>
