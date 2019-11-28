<template>
    <div>
        <search-panel :ref="spMeta['name']" :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="tlMeta['name']" :meta="tlMeta">
            <template #operation-bar="{conf}">
                <el-button v-bind="conf" @click="addConf">新增</el-button>
            </template>
            <template #buttons="{conf, scope}">
                <el-button-group>
                    <el-tooltip content="配置" placement="left">
                        <el-button icon="el-icon-s-tools" v-bind="conf['edit']['conf']" type="info"
                                   @click="handlerConf($event, scope.row, scope.$index)"></el-button>
                    </el-tooltip>
                    <el-tooltip :content="conf['edit']['label']" placement="top">
                        <el-button v-bind="conf['edit']['conf']"
                                   @click="ref.handleEdit($event, scope.row, scope.$index)"></el-button>
                    </el-tooltip>
                    <el-tooltip :content="conf['delete']['label']" placement="right">
                        <el-button v-bind="conf['delete']['conf']"
                                   @click="ref.handleDelete($event, scope.row, scope.$index)"></el-button>
                    </el-tooltip>
                </el-button-group>
            </template>
        </table-list>
    </div>
</template>

<script>
    import {getSpMeta, getTlMeta} from "../../core/mixins/methods"

    export default {
        name: "GlobalConfList",
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
            handleSearch(params) {
                this.$refs[this.tlMeta['name']].getData(params);
            },
            addConf() {
                this.$router.push("/main/global-conf");
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
