<template>
  <div class="page-container">
    <row-grid :span="[6, 18]">
        <template #0>
            <div class="el-card">
                <data-list :ref="dlRefName" :meta="dlMeta" @active-change="handleActiveChange"
                           @chose-change="handleChoseChange"></data-list>
            </div>
        </template>
        <template #1>
            <div class="el-card" style="margin-left: 5px;">
                <search-view :meta="spMeta" @search="handleSearch"></search-view>
                <table-view :ref="tlRefName" :meta="tlMeta" :filter-params="filterParams" :page="{ size: 5 }">
                    <template #prefix-btn="{conf}">
                        <slot name="prefix-btn" v-bind:conf="conf"></slot>
                    </template>
                    <template #add-btn="{conf, add}">
                        <slot name="add-btn" v-bind:conf="conf" v-bind:add="add"></slot>
                    </template>
                    <template #batch-delete-btn="{conf, batchDelete}">
                        <slot name="batch-delete-btn" v-bind:conf="conf"
                              v-bind:batchDelete="batchDelete">
                        </slot>
                    </template>
                    <template #suffix-btn="{conf}">
                        <slot name="suffix-btn" v-bind:conf="conf"></slot>
                    </template>

                    <template #buttons="{scope, conf}">
                        <slot name="buttons" v-bind:conf="conf" v-bind:scope="scope"></slot>
                    </template>
                </table-view>
            </div>
        </template>
    </row-grid>
  </div>
</template>

<script>
    import utils from '../utils'
    import {loadFeature, getDataListMeta, getSearchViewMeta, getTableViewMeta} from '../utils/rest'

    export default {
        name: "DataListTableTmpl",
        meta: {
          isTemplate: true,
          isPage: false,
          cn: '左列-右表模板',
          icon: 'list_table',
          buildIn: true // 内建：DbMeta提供
        },
        props: {
            fc: String
        },
        data() {
            const {fc: R_fc} = this.$route.query;
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
            refresh() {
                const {$refs, tlRefName} = this;
                $refs[tlRefName].getData();
            },
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
                getDataListMeta(this.$axios, dlObjectCode).then(resp => {
                    this.dlMeta = resp.data;
                }).catch(({msg = '获取DataList meta发生错误'}) => {
                    console.error('[ERROR] msg: %s', msg);
                });

                getSearchViewMeta(this.$axios, dlObjectCode).then(resp => {
                    this.spMeta = resp.data;
                }).catch(({msg = '加载SearchView meta数据发生错误'}) => {
                    console.error('[ERROR] msg: %s', msg);
                });

                getTableViewMeta(this.$axios, tlObjectCode).then(resp => {
                    let tlMeta = resp.data;
                    const {foreignFieldCode} = this.tlConf;
                    const data_url = tlMeta['data_url'] + '?' + foreignFieldCode + '={objectCode}'; // pxg_todo 关联方式
                    tlMeta['data_url'] = data_url;
                    this.tableUrl = data_url;
                    this.tlMeta = tlMeta;
                }).catch(({msg = '获取TableView meta发生错误'}) => {
                    console.error('[ERROR] msg: %s', msg);
                });
            }
        },
        created() {
          loadFeature(this.$axios, this.featureCode).then(resp => {
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
