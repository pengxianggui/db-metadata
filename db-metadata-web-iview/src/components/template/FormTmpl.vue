<!--
    容器型组件-路由指向入口
    ps: @/components/core/FormTmpl应该更名为FormBox
-->
<template>
    <FormTmpl :ref="meta['name']" :meta="meta"></FormTmpl>
</template>

<script>
    export default {
        name: "FormTmpl",
        props: {
            R_oc: String
        },
        data() {
            return {
                objectCode: this.R_oc,
                meta: {}
            }
        },
        methods: {
            getMeta() {
                // TODO 需要单独一个toUpdate接口，只需要传入objectCode参数, 此处url待定
                let url = this.$compile('/form/toUpdate/meta_object?objectCode={objectCode}', {
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