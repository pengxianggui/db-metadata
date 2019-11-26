<template>
    <el-container direction="horizontal" style="display: flex; padding: 5px;">
        <span style="flex: 1">Header 占坑</span>
        <el-tooltip class="item" effect="dark" content="初始化数据库" placement="bottom">
            <el-button @click="initDb" type="primary" icon="el-icon-magic-stick" size="mini"></el-button>
        </el-tooltip>
        <el-tooltip class="item" effect="dark" content="清空数据库" placement="bottom">
            <el-button @click="cleanDb" type="danger" icon="el-icon-delete-solid" size="mini"></el-button>
        </el-tooltip>
    </el-container>
</template>

<script>
    export default {
        name: "HeaderBar",
        methods: {
            initDb: function () {
                this.$axios.get('/db/init/hello').then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            cleanDb: function () {

                this.$confirm('此操作将清空数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.$axios.get('/db/truncate/hello').then(resp => {
                        this.$message.success(resp.msg);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    })
                }).catch(() => {
                });


            }
        }
    }
</script>

<style scoped>

</style>