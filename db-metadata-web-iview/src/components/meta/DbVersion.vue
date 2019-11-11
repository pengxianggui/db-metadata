<template>
    <table-list :ref="meta['name']" :meta="meta"></table-list>
</template>

<script>
    export default {
        name: "DbVersion",
        data() {
            return {
                meta: {}
            }
        },
        methods: {
            getMeta() {
                this.$axios.get("/meta/fields/pdman_db_version")
                .then(resp => {
                    this.meta = resp.data;
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            }
        },
        created() {
            this.getMeta();
        }
    }
</script>

<style scoped>

</style>
