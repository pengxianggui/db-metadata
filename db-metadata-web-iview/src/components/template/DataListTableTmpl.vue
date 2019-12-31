<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <div class="el-card">
                <data-list :ref="dlRefName" :meta="dlMeta" @active-change="handleActiveChange"
                           @chose-change="handleChoseChange"></data-list>
            </div>
        </template>
        <template #right>
            <div class="el-card" style="margin-left: 5px;">
                <search-panel :meta="spMeta" @search="handleSearch"></search-panel>
                <table-list :ref="tlRefName" :meta="tlMeta" :filter-params="filterParams" :page="{ size: 5 }"></table-list>
            </div>
        </template>
    </row-grid>
</template>

<script>
    import utils from '@/utils'
    import {getDlMeta, getSpMeta, getTlMeta, loadFeature} from "@/components/core/mixins/methods"

    export default {
        name: "DataListTableTmpl",
        mixins: [loadFeature, getTlMeta, getSpMeta, getDlMeta],
        props: {
            fc: String
        },
        data() {
            const {featureCode: R_fc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);
            return {
                featureCode: featureCode,
                dlConf: {},
                tlConf: {},
                dlMeta: {},
                spMeta: {},
                tlMeta: {},
                filterParams: {},
                tableUrl: null
            }
        },
        methods: {
            handleChoseChange(rows) {
                // pxg_todo
            },
            handleActiveChange(row) {
                const {primaryKey} = this.dlConf;

                this.tlMeta['data_url'] = this.$compile(this.tableUrl, {
                    objectCode: row[primaryKey]
                });
            },
            handleSearch(params) {
                const {tableRefName} = this;
                this.filterParams = params;
                this.$nextTick(() => {
                    this.$refs[tableRefName].getData();
                });
            },
            initMeta(dlObjectCode, tlObjectCode) {
                this.getDlMeta(dlObjectCode).then(resp => {
                    this.dlMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getSpMeta(dlObjectCode).then(resp => {
                    this.spMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getTlMeta(tlObjectCode).then(resp => {
                    let tlMeta = resp.data;
                    const {foreignFieldCode} = this.tlConf;
                    const data_url = tlMeta['data_url'] + '?' + foreignFieldCode + '={objectCode}'; // pxg_todo 关联方式
                    tlMeta['data_url'] = data_url;
                    this.tableUrl = data_url;
                    this.tlMeta = tlMeta;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            }
        },
        created() {
            this.loadFeature(this.featureCode).then(resp => {
                const feature = resp.data;
                this.dlConf = feature['list'];
                this.tlConf = feature['table'];
                const dlObjectCode = this.dlConf['objectCode'];
                const tlObjectCode = this.tlConf['objectCode'];

                this.initMeta(dlObjectCode, tlObjectCode);
            });
        },
        computed: {
            dlRefName() {
                return this.dlMeta['name'];
            },
            tlRefName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>