<template>
    <vue-json-editor v-model="nativeValue"
                     v-bind="innerMeta.conf">
    </vue-json-editor>
</template>

<script>
    import {DEFAULT} from '@/constant'
    import vueJsonEditor from 'vue-json-editor'
    import Meta from '../mixins/meta'
    import {Val} from './value-mixins'

    let format = function (value) {
        try {
            return JSON.parse(value);
        } catch (e) {
            console.warn('value is not legal, a empty object {} will be replaced');
            return {};
        }
    };

    export default {
        mixins: [Meta(DEFAULT.JsonBox), Val(format)],
        components: {
            vueJsonEditor
        },
        props: {
            value: [Object, String],
        },
    };
</script>
