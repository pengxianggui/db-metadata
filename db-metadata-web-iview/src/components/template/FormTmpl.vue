<!--
    容器型组件-路由指向入口
    ps: @/components/core/FormTmpl应该更名为FormBox
-->
<template>
    <FormBox :ref="meta['name']" :meta="meta"></FormBox>
</template>

<script>
    import utils from '@/utils'
    import FormTmpl from "@/components/core/form/src/FormTmpl";

    export default {
        name: "FormTmpl",
        components: {
            "FormBox": FormTmpl
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