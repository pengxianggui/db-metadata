<template>
    <div class="page-container">
        <search-view :meta="spMeta" @search="handleSearch"></search-view>
        <table-view :ref="refName" :meta="tlMeta" :filter-params="filterParams">
            <template #add-btn="{conf}">
<!--                <el-button v-bind="conf.conf" @click="addConf" icon="el-icon-document-add">新增</el-button>-->
              <span></span>
            </template>
            <template #batch-delete-btn><span></span></template>
            <template #inner-before-extend-btn="{scope, conf}">
                <el-tooltip content="配置" placement="left">
                    <el-button icon="el-icon-s-tools" v-bind="conf.conf"
                               @click="handlerConf($event, scope.row, scope.$index)"></el-button>
                </el-tooltip>
            </template>
        </table-view>
    </div>
</template>

<script>
    import {getSearchViewMeta, getTableViewMeta} from "../../utils/rest";
    import {routeUrl} from '../../constant/url'
    import {metaObjectCode} from "../../constant/variable";

    export default {
        name: "GlobalConfList",
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
                objectCode: metaObjectCode.globalConfList,
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
            getTableViewMeta(this.$axios, this.objectCode).then(resp => {
                this.tlMeta = resp.data;
            }).catch(({msg = '获取TableView Meta数据发生错误'}) => {
                console.error('[ERROR] msg: %s', msg);
            });

            getSearchViewMeta(this.$axios, this.objectCode).then(resp => {
                this.spMeta = resp.data;
            }).catch(({msg = '获取SearchView meta数据发生错误'}) => {
                console.error('[ERROR] msg: %s', msg);
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
