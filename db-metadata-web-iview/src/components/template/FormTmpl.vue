<!--
    容器型组件-路由指向入口
    ps: @/components/core/FormTmpl应该更名为FormBox
-->
<template>
    <FormBox :ref="meta['name']" :meta="meta"></FormBox>
</template>

<script>
    import FormTmpl from "@/components/core/FormTmpl";

    export default {
        name: "FormTmpl",
        components: {
            "FormBox": FormTmpl
        },
        data() {
            return {
                objectCode: this.$route.query.objectCode,
                meta: {}
            }
        },
        methods: {
            getMeta() {
                let url = this.$compile('/form/toUpdate?objectCode={objectCode}', {
                    objectCode: this.objectCode
                });

                this.$axios.get(url).then(resp => {
                    this.meta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                    this.$message.error(err.msg);
                })
            },
        },
        created() {
            this.getMeta();
        },
    }
</script>

<style scoped>

</style>