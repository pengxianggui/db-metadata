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
        name: "InstanceConfList",
        data() {
            return {
                meta: {}
            }
        },
        methods: {
            addConf() {
                this.$router.push("/main/instance-conf");
            },
            getMeta() {
                this.$axios.get("/meta/fields/meta_component_instance")
                    .then(resp => {
                        this.meta = resp.data;
                    }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            handlerConf(ev, row, index) {
                if (ev) ev.stopPropagation();
                let componentCode = row['comp_code'];
                let objectCode = row['dest_object'];
                this.$router.push({
                    path: 'instance-conf',
                    query: {
                        componentCode: componentCode,
                        objectCode: objectCode
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
