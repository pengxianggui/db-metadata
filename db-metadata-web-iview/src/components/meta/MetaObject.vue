<template>
    <div>
        <template v-if="tableMeta">
            <table-list :meta="tableMeta"></table-list>
        </template>
    </div>
</template>

<script>
    import TableList from '@/components/feature/TableList'

    export default {
        name: "meta-object",
        data() {
            return {
                tableMeta: null,
                // tableData: null
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
