<template>
    <table-list v-if="tableMeta" :ref="tableMeta['name']" :meta="tableMeta"></table-list>
</template>

<script>
    export default {
        name: "TableData",
        data() {
            return {
                tableMeta: null
            }
        },
        methods: {
            getTableMeta() {
                this.$axios.get("/meta/fields/meta_component")
                .then(resp => {
                    this.tableMeta = resp.data;
                    // this.$refs[this.tableMeta['name']].getData();
                }).catch(err => {
                    this.$message({type: 'error', message: err})
                })
            }
        },
        mounted() {
            this.getTableMeta();
        }
    }
</script>

<style scoped>

</style>
