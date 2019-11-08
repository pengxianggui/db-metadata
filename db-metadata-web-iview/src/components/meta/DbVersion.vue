<template>
    <table-list v-if="meta" :ref="meta['name']" :meta="meta"></table-list>
</template>

<script>
    export default {
        name: "DbVersion",
        data() {
            return {
                meta: null
            }
        },
        methods: {
            getMeta() {
                this.$axios.get("/meta/fields/PDMAN_DB_VERSION")
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
