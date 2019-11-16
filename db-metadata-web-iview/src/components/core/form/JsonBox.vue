<template>
    <vue-json-editor v-model="nativeValue"
                     :mode="mode"
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
            if (typeof value == 'string') {
                return JSON.parse(value);
            } else { // object
                return JSON.parse(JSON.stringify(value));
            }
        } catch (e) {
            console.error('value is not legal, a empty object {} will be replaced, value: %o', value);
            return {};
        }
    };

    export default {
        mixins: [Meta(DEFAULT.JsonBox), Val(format)],
        name: "JsonBox",
        label: "Json框",
        components: {
            vueJsonEditor
        },
        props: {
            value: [Object, String],
            mode: String
        },
    };
</script>
