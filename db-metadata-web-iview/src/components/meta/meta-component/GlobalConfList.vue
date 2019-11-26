<template>
    <table-list :ref="meta['name']" :meta="meta">
        <template #operation-bar>
            <el-button @click="addConf">新增</el-button>
        </template>
        <template #buttons="{scope}">
            <el-tooltip content="配置" placement="top">
                <el-button icon="el-icon-s-tools" circle
                           @click="handlerConf($event, scope.row, scope.$index)"></el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="top">
                <el-button icon="el-icon-edit" circle
                           @click="ref.handleEdit($event, scope.row, scope.$index)"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
                <el-button icon="el-icon-delete" circle type="danger"
                           @click="ref.handleDelete($event, scope.row, scope.$index)"></el-button>
            </el-tooltip>
        </template>
    </table-list>
</template>

<script>
    export default {
        name: "GlobalConfList",
        data() {
            return {
                meta: {}
            }
        },
        methods: {
            addConf() {
                this.$router.push("/main/global-conf");
            },
            getMeta() {
                this.$axios.get("/meta/fields/meta_component")
                    .then(resp => {
                        this.meta = resp.data;
                    }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            handlerConf(ev, row, index) {
                if (ev) ev.stopPropagation();
                let componentCode = row.code;
                this.$router.push({
                    path: 'global-conf',
                    query: {
                        componentCode: componentCode
                    }
                })
            }
        },
        created() {
            this.getMeta();
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
