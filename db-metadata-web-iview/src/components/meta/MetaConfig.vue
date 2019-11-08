<template>
    <table-list v-if="meta" :ref="meta['name']" :meta="meta"></table-list>
</template>

<script>
    export default {
        name: "MetaConfig",
        data() {
            return {
                meta: null
            }
        },
        methods: {
            getMeta() {
                this.$axios.get("/meta/fields/meta_config")
                .then(resp => {
                    this.meta = resp.data;
                    // this.$refs[this.tableMeta['name']].getData();
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            }
        },
        mounted() {
            this.getMeta();
        }
    }
</script>

<style scoped>

</style>
