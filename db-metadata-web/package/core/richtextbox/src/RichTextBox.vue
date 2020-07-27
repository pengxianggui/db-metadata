<template>
    <editor v-model="nativeValue" :api-key="apiKey" :init="editorConf"/>
</template>

<script>
    // TODO 将一些配置提取支持meta传入
    import Editor from '@tinymce/tinymce-vue'
    import {EditorConf, apiKey} from './config';
    import Val from "../../mixins/value";

    export default {
        name: "RichTextBox",
        mixins: [Val()],
        components: {
            'editor': Editor
        },
        props: {
            value: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                editorConf: EditorConf,
                apiKey: apiKey
            }
        }
    }
</script>

<style scoped>

</style>