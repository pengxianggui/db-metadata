<template>
    <table-list :ref="meta['name']" :meta="meta"></table-list>
</template>

<script>
    export default {
        name: "TestTable",
        data() {
            return {
                meta: {}
            }
        },
        methods: {
            getMeta() {
                this.$axios.get("/meta/fields/test_table")
                .then(resp => {
                    this.meta = resp.data;
                }).catch(err => {
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
