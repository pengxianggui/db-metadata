<template>
    <div>
        <h2>{{componentName}}</h2>
        <vue-markdown :source="getSourceCode(componentName)"></vue-markdown>
    </div>
</template>

<script>
    import VueMarkdown from 'vue-markdown'
    import {uiConfDesc} from "@/components/core";

    export default {
        name: "UiConfTip",
        components: {
            'vue-markdown': VueMarkdown
        },
        props: {
            componentName: {
                type: String,
                required: true,
                default: () => ''
            }
        },
        data() {
            return {
                uiConfDescs: uiConfDesc,
            }
        },
        methods: {
            getSourceCode(componentName) {
                const confDesc = this.uiConfDescs[componentName];
                return confDesc;
            }
        }

    }
</script>

<style scoped>

</style>