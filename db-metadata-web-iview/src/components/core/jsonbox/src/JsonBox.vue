<template>
    <vue-json-editor v-model="nativeValue"
                     :mode="mode"
                     v-bind="$reverseMerge(innerMeta.conf, $attrs)">
    </vue-json-editor>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import vueJsonEditor from 'vue-json-editor'
    import Meta from '../../mixins/meta'
    import Val from '../../mixins/value'
    import conver from './conver'

    export default {
        mixins: [Meta(DEFAULT.JsonBox), Val(conver)],
        name: "JsonBox",
        label: "Json框",
        components: {
            vueJsonEditor
        },
        props: {
            value: [Object, String, Array],
            mode: String
        },
    };
</script>
