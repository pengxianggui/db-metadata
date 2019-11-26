<template>
    <div>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta">
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
    </div>
</template>

<script>
    export default {
        name: "InstanceConfList",
        data() {
            return {
                spMeta: {},
                tlMeta: {}
            }
        },
        methods: {
            addConf() {
                this.$router.push("/main/instance-conf");
            },
            getTlMeta() {
                this.$axios.get("/meta/fields/meta_component_instance")
                    .then(resp => {
                        this.tlMeta = resp.data;
                    }).catch(err => {
                    this.$message.error(err.msg);
                })
            },
            getSpMeta() {
                let url = this.$compile("/component/meta?componentCode=SearchPanel&objectCode=meta_component_instance", {
                    objectCode: this.objectCode
                });
                this.$axios.get(url).then(resp => {
                    this.spMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] url: %s, msg: %s', url, err.msg);
                    this.$message.error(err.msg);
                });
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
            },
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
            }
        },
        created() {
            this.getTlMeta();
            this.getSpMeta();
        },
        computed: {
            ref() {
                return this.$refs[this.tlMeta['name']];
            }
        }
    }
</script>

<style scoped>

</style>
