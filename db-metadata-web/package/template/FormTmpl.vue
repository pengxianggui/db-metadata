<!--
    容器型组件-路由指向入口
    ps: @/components/core/FormTmpl应该更名为FormBox
-->
<template>
    <form-view :ref="meta['name']" :meta="meta">
        <template #action="{model, conf}">
            <slot name="action" v-bind:model="model" v-bind:conf="conf"></slot>
        </template>
    </form-view>
</template>

<script>
    import utils from '../utils'
    import FormView from "../core/formview/src/FormView";

    export default {
        name: "FormTmpl",
        components: {
            FormView
        },
        props: {
            metaUrl: {
                type: String,
                require: true
            }
        },
        data() {
            const {R_url} = this.$route.query;
            const url = utils.assertUndefined(this.metaUrl, R_url);
            return {
                url: url,
                meta: {}
            }
        },
        created() {
            const {url} = this;
            this.$axios.get(url).then(resp => {
                this.meta = resp.data;
            });
        }
    }
</script>

<style scoped>

</style>
