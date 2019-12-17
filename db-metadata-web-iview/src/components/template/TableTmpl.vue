<!--
    容器型组件-路由指向入口
-->
<template>
    <table-list :ref="meta['name']" :meta="meta"></table-list>
</template>

<script>
    import {getTlMeta} from "@/components/core/mixins/methods";

    export default {
        name: "TableTmpl",
        mixins: [getTlMeta],
        data() {
            return {
                objectCode: this.$route.query.objectCode,
                meta: {}
            }
        },
        created() {
            this.getTlMeta(this.objectCode).then(resp => {
                this.meta = resp.data;
            }).catch(err => {
                console.error('[ERROR] url: %s, msg: %s', err.msg);
                this.$message.error(err.msg);
            })
        },
    }
</script>

<style scoped>

</style>
