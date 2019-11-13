<template>
    <table-list :ref="meta['name']" :meta="meta">
        <template #operation>
            <el-button @click="handleBatchDelete($event)" type="danger">删除</el-button>
        </template>
    </table-list>
</template>

<script>
    export default {
        name: "MetaObject",
        data() {
            return {
                meta: {}
            }
        },
        methods: {
            getTableMeta() {
                const url = '/meta/objs';
                this.$axios.get(url).then(resp => {
                    this.meta = resp.data;
                    this.$nextTick(() => {
                        this.ref.doDelete = this.doDelete; // override doDelete
                    });
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg ? err.msg : err);
                    this.$message.error(err.msg)
                });
            },
            handleBatchDelete(ev) {
                this.ref.handleBatchDelete(ev);
            },
            doDelete(ids, ev, row, index) {
                let objectCodes;
                if (Array.isArray(ids)) {
                    const objectCodeArr = this.ref.innerChoseData.map(row => row.code);
                    objectCodes = objectCodeArr.join(',');
                } else {
                    objectCodes = row.code;
                }
                let title = '<div style="overflow: auto;">确定删除如下元对象? ' + objectCodes + '</div>';
                let url = '/meta/delete?objectCode=' + objectCodes;

                this.$confirm(title, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    dangerouslyUseHTMLString: true
                }).then(() => {
                    this.$axios.delete(url).then(resp => {
                        this.$message.success(resp.msg);
                        this.getData();
                    }).catch(err => {
                        this.$message.error(err.msg);
                    });
                });
            }
        },
        created() {
            this.getTableMeta()
        },
        computed: {
            ref() {
                return this.$refs[this.meta['name']];
            }
        }
    }
</script>

<style scoped>

</style>