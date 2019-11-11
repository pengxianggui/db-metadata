<template>
    <el-container direction="horizontal" style="display: flex; padding: 5px;">
        <span style="flex: 1">Header 占坑</span>
        <el-button @click="initDb" type="primary">初始化数据库</el-button>
        <el-button @click="cleanDb" type="danger">清空数据库</el-button>
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
                this.$axios.get('/db/truncate/hello').then(resp => {
                    this.$message.success(resp.msg);
                }).catch(err => {
                    this.$message.error(err.msg);
                })
            }
        }
    }
</script>

<style scoped>

</style>