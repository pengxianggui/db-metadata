<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <div class="el-card">
                <data-list :ref="dlRefName" :meta="dlMeta" @active-change="handleActiveChange"></data-list>
            </div>
        </template>
        <template #right>
            <div class="el-card" style="margin-left: 5px;">
                <search-panel :meta="spMeta" @search="handleSearch"></search-panel>
                <table-list :ref="tlRefName" :meta="tlMeta" :page="{ size: 5 }"></table-list>
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
        props: ["fc"],
        data() {
            const featureCode = utils.assertUndefined(this.fc, this.$route.query.featureCode);
            return {
                featureCode: featureCode,
                dlConf: {},
                tlConf: {},
                dlMeta: {},
                spMeta: {},
                tlMeta: {},
                tableUrl: null
            }
        },
        methods: {
            handleActiveChange(row) {
                const {primaryKey} = this.dlConf;

                this.tlMeta['data_url'] = this.$compile(this.tableUrl, {
                    objectCode: row[primaryKey]
                });
            },
            handleSearch(params) {

            }
        },
        created() {
            this.loadFeature(this.featureCode).then(resp => {
                const feature = resp.data;
                this.dlConf = feature['dataList'];
                this.tlConf = feature['tableList'];

                const dlObject = this.dlConf['objectCode'];
                const tlObject = this.tlConf['objectCode'];
                const foreignFieldCode = this.tlConf['foreignFieldCode'];

                this.getDlMeta(dlObject).then(resp => {
                    this.dlMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getSpMeta(dlObject).then(resp => {
                    this.spMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getTlMeta(tlObject).then(resp => {
                    let tlMeta = resp.data;
                    const data_url = tlMeta['data_url'] + '?' + foreignFieldCode + '={objectCode}'; // pxg_todo 关联方式
                    tlMeta['data_url'] = data_url;
                    this.tableUrl = data_url;
                    this.tlMeta = tlMeta;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
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