<template>
    <div>
        <search-panel :meta="spMeta" @search="handleSearch"></search-panel>
        <table-list :ref="refName" :meta="tlMeta" :filter-params="filterParams">
            <template #add-btn="{conf}">
                <el-button v-bind="conf" @click="addConf" icon="el-icon-document-add">新增</el-button>
            </template>
            <template #batch-delete-btn><span></span></template>
            <template #inner-before-extend-btn="{conf, scope}">
                <el-tooltip content="配置" placement="left">
                    <el-button icon="el-icon-s-tools" v-bind="conf" type="info"
                               @click="handlerConf($event, scope.row, scope.$index)"></el-button>
                </el-tooltip>
            </template>
        </table-list>
    </div>
</template>

<script>
    import {getSpMeta, getTlMeta} from "../../core/mixins/methods"
    import {routeUrl} from '../../constant/url'
    import {innerObjectCode} from "../../constant/variable";

    export default {
        name: "GlobalConfList",
        mixins: [getTlMeta, getSpMeta],
        props: {
            newRouteUrl: {
                type: String,
                default: () => routeUrl.baseURL + routeUrl.R_GOBAL_CONF_ADD
            },
            editRouteUrl: {
                type: String,
                default: () => routeUrl.baseURL + routeUrl.R_GOBAL_CONF_EDIT
            }
        },
        data() {
            return {
                objectCode: innerObjectCode.globalConfList,
                tlMeta: {},
                filterParams: {},
                spMeta: {}
            }
        },
        methods: {
            handleSearch(params) {
                const {refName} = this;
                this.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[refName].getData();
                });
            },
            addConf() {
                this.$router.push(this.newRouteUrl);
            },
            handlerConf(ev, row, index) {
                if (ev) ev.stopPropagation();
                let componentCode = row['code'];
                // const url =
                this.$router.push({
                    path: this.editRouteUrl,
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
            refName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>
