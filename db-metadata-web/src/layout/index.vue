<template>
    <admin-layout>
        <template #header>
            <div>
                <span class="h2" style="font-family: unset">元数据管理系统</span>
                <span style="font-family: cursive;">—— Data Fuck Everything</span>
            </div>
            <span style="flex: 1"></span>
            <router-link to="/workspace" style="cursor: pointer;">
                workspace
            </router-link>
            &nbsp;&nbsp;
            <el-tooltip class="item" effect="dark" content="初始化数据库" placement="bottom">
                <el-button @click="initDb" type="primary" icon="el-icon-magic-stick" size="mini"></el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="清空数据库" placement="bottom">
                <el-button @click="cleanDb" type="danger" icon="el-icon-delete-solid" size="mini"></el-button>
            </el-tooltip>
        </template>
        <template #menu>
            <nav-menu :unique-opened="true"></nav-menu>
        </template>
    </admin-layout>
</template>

<script>
    export default {
        name: "index",
        methods: {
            initDb: function () {
                this.$prompt('提示:请输入口令', '确定要初始化系统', {}).then(data => {
                    this.$axios.get('/db/init/' + data.value).then(resp => {
                        this.$message.success(resp.msg);
                        this.$router.go(0);
                    }).catch(err => {
                        this.$message.error(err.msg);
                    })
                })
            },
            cleanDb: function () {
                this.$prompt('提示:请输入口令', '确定要清空数据库', {}).then(data => {
                    this.$axios.get('/db/truncate/' + data.value).then(resp => {
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