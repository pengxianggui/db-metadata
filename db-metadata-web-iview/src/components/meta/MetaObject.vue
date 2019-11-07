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
                const url = '/meta/objs';
                this.$axios.get(url).then(resp => {
                    this.tableMeta = resp.data;
                    this.tableMeta['objectCode'] = 'meta_object';
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                    this.$message.error(err.msg)
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
