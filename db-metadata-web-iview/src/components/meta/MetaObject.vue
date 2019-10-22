<template>
    <table-list :meta="tableMeta" v-if="tableMeta" :data="tableData"></table-list>
</template>

<script>
    import TableList from '@/components/feature/TableList'
    export default {
        name: "meta-object",
        data() {
            return {
                tableMeta: {},
                tableData: []
            }
        },
        methods: {
            getTableMeta() {
                let _this = this;
                this.$axios.get('/meta/objs').then(resp => {
                    _this.tableMeta = resp.data
                }).catch(resp => {
                    _this.$message.error(resp.toString())
                })
            },
        },
        created() {
            this.getTableMeta()
        },
        mounted() {
            // this.getTableData()
        },
        components: {
            TableList
        },
    }
</script>

<style scoped>

</style>
