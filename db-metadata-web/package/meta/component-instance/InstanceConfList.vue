<template>
    <div>
        <search-view :meta="spMeta" @search="handleSearch"></search-view>
        <table-view :ref="refName" :meta="tlMeta" :filter-params="filterParams">
            <template #add-btn="{conf}">
                <el-button v-bind="conf" @click="addConf" icon="el-icon-document-add">新增</el-button>
            </template>
            <template #inner-before-extend-btn="{scope}">
                <el-tooltip content="配置" placement="left">
                    <el-button icon="el-icon-s-tools" size="mini" type="info"
                               @click="handlerConf($event, scope.row, scope.$index)"></el-button>
                </el-tooltip>
            </template>
        </table-view>
    </div>
</template>

<script>
    import utils from '../../utils'
    import {routeUrl} from "../../constant/url";
    import {metaObjectCode} from "../../constant/variable";
    import {getSpMeta, getTlMeta} from "../../core/mixins/methods"

    export default {
        name: "InstanceConfList",
        mixins: [getTlMeta, getSpMeta],
        props: {
            newRouteUrl: {
                type: String,
                default: () => routeUrl.baseURL + routeUrl.R_INSTANCE_CONF_NEW
            },
            editRouteUrl: {
                type: String,
                default: () => routeUrl.baseURL + routeUrl.R_INSTANCE_CONF_EDIT
            }
        },
        data() {
            return {
                objectCode: metaObjectCode.instanceConfList,
                tlMeta: {},
                filterParams: {},
                spMeta: {}
            }
        },
        methods: {
            addConf() {
                const url = utils.compile(this.newRouteUrl, {
                    componentCode: '',
                    objectCode: ''
                });
                this.$router.push(url);
            },
            handlerConf(ev, row, index) {
                if (ev) ev.stopPropagation();
                let instanceCode = utils.convertToString(row['code']);
                let componentCode = utils.convertToString(row['comp_code']);
                let objectCode = utils.convertToString(row['dest_object']);
                const url = utils.compile(this.editRouteUrl, {
                    instanceCode: instanceCode,
                    componentCode: componentCode,
                    objectCode: objectCode
                });
                this.$router.push(url);
            },
            handleSearch(params) {
                const {refName} = this;
                this.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[refName].getData();
                });
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
