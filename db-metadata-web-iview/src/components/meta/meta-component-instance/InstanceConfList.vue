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
    import {getTlMeta, getSpMeta} from "../../core/mixins/methods"

    export default {
        name: "InstanceConfList",
        mixins: [getTlMeta, getSpMeta],
        props: {
            R_oc: String
        },
        data() {
            return {
                objectCode: this.R_oc,
                tlMeta: {},
                spMeta: {}
            }
        },
        methods: {
            addConf() {
                this.$router.push("/main/instance-conf");
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
                this.ref.getData(params);
            }
        },
        created() {
            this.getTlMeta(this.objectCode).then(resp => {
                this.tlMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });

            this.getSpMeta(this.objectCode).then(resp => {
                this.spMeta = resp.data;
            }).catch(err => {
                console.error('[ERROR] msg: %s', err.msg);
                this.$message.error(err.msg);
            });
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
