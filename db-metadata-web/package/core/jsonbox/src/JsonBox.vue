<template>
    <vue-json-editor v-model="nativeValue"
                     :mode="innerMode"
                     v-bind="$reverseMerge(meta.conf, $attrs)">
    </vue-json-editor>
</template>

<script>
    import vueJsonEditor from 'vue-json-editor'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import conver from './conver'
    import reverse from "./reverse";
    import DefaultMeta from '../ui-conf'
    import utils from '@/../package/utils'

    export default {
        mixins: [Meta(DefaultMeta), Val(conver, reverse)],
        name: "JsonBox",
        label: "Json框",
        components: {
            vueJsonEditor
        },
        props: {
            value: [Object, String, Array],
            mode: String
        },
        computed: {
          innerMode() {
            const {$attrs: {mode: attrMode} = {}, meta: {conf: {mode: metaMode} = {}}} = this
            return utils.assertEmpty(attrMode, metaMode);
          }
        }
    };
</script>
