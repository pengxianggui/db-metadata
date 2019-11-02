<template>
    <vue-json-editor v-model="nativeValue"
                     :modes="innerMeta.conf['modes']"
                     :mode="innerMeta.conf['mode']">
    </vue-json-editor>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import vueJsonEditor from 'vue-json-editor'
    import Meta from '../mixins/meta'
    import Val from './value-mixins'

    export default {
        mixins: [Meta(DEFAULT.JsonBox), Val],
        components: {
            vueJsonEditor
        },
        props: {
            value: Object,
        },
    };
</script>
