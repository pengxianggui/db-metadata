<template>
    <div>
        <template v-if="tableMeta">
            <table-list :meta="tableMeta"></table-list>
        </template>
    </div>
</template>

<script>
    export default {
        name: "meta-object",
        data() {
            return {
                tableMeta: null,
            }
        },
        methods: {
            getTableMeta() {
                this.$axios.get('/meta/objs').then(resp => {
                    this.tableMeta = resp.data;
                    this.tableMeta['objectCode'] = 'meta_object';
                }).catch(resp => {
                    this.$message.error(resp.toString())
                })
            },
        },
        created() {
            this.getTableMeta()
        },
    }
</script>

<style scoped>

</style>
