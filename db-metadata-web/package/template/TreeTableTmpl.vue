<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <tree-view :ref="treeRefName" :meta="treeMeta" @active-change="handleActiveChange" @chose-change="handleChoseChange"
            ></tree-view>
        </template>
        <template #right>
            <table-view :ref="tlRefName" :meta="tvMeta">
                <template #prefix-btn="{conf}">
                    <slot name="prefix-btn" v-bind:conf="conf"></slot>
                </template>
<!--                <template #add-btn="{conf}">-->
<!--                    <slot name="add-btn" v-bind:conf="conf"></slot>-->
<!--                </template>-->
                <template #add-btn="{conf}">
                  <slot name="add-btn" v-bind:conf="conf">
                    <el-button v-bind="conf" @click="handleAdd">新增</el-button>
                  </slot>
                </template>
                <template #batch-delete-btn="{conf}">
                    <slot name="batch-delete-btn" v-bind:conf="conf"></slot>
                </template>
                <template #suffix-btn="{conf}">
                    <slot name="suffix-btn" v-bind:conf="conf"></slot>
                </template>

                <template #buttons="{scope, conf}">
                    <slot name="buttons" v-bind:conf="conf" v-bind:scope="scope"></slot>
                </template>
            </table-view>
        </template>
    </row-grid>
</template>

<script>
    import utils from '../utils'
    import {getMetaFromFeature_TreeTableTmpl, getTableViewMeta, getTreeMeta, loadFeature} from "../utils/rest";
    import {restUrl} from "..";
    import {isEmpty} from "../utils/common";
    import {resolvePath} from "../utils/url";

    export default {
        name: "TreeTableTmpl",
        props: {
            fc: String
        },
        data() {
            const {featureCode: R_fc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);

            return {
                featureCode: featureCode,
                treeConf: {},
                activeTreeData: {},
                tableConf: {},
                tvMeta: {},
                treeMeta: {},
                tableDataUrl: ''
            }
        },
        methods: {
            refresh() {
                this.refreshTreeData()
                this.refreshTableData()
            },
            handleChoseChange(rows) {
                // pxg_todo 多选树节点时, table的默认行为(待定)
            },
            refreshTreeData() {
                const {treeRefName, $refs} = this
                $refs[treeRefName].getData()
            },
            refreshTableData() {
                const {treeConf: {primaryKey}, activeTreeData, tableDataUrl} = this

                const foreignFieldValue = activeTreeData[primaryKey];
                let url = this.$compile(tableDataUrl, {foreignFieldValue: foreignFieldValue});
                this.tvMeta['data_url'] = url;
            },
            handleActiveChange(row) {
                this.activeTreeData = row
                this.refreshTableData()
            },
            handleAdd() {
              if (utils.isEmpty(this.activeTreeData)) {
                this.$message.warning('请先选择树节点', '提示');
                return;
              }

              const {$refs, tlRefName, featureCode, activeTreeData, treeConf,
                tableConf: {objectCode: tableObjectCode, foreignFieldCode: foreignKeyName}} = this;

              const foreignKeyValue = activeTreeData[foreignKeyName];
              // TODO

              // const url = this.$compile(restUrl.MASTER_SLAVE_TO_ADD_S, {
              //   objectCode: tableObjectCode,
              //   featureCode: featureCode,
              //   foreignKeyName: foreignKeyName,
              //   foreignKeyValue: foreignKeyValue
              // });

              $refs[tlRefName].dialog(url);
            },
            adjustTvMeta() {
                const {tableConf: {foreignFieldCode}, tvMeta} = this
                let data_url = tvMeta['data_url'];
                if (!isEmpty(foreignFieldCode)) {
                  let params = {};
                  params[foreignFieldCode] = "{foreignFieldValue}"
                  data_url = resolvePath(data_url, params)
                }
                this.tableDataUrl = data_url;
                this.tvMeta['data_url'] = data_url
            },
            adjustTreeMeta() {
                const {treeConf: {label}} = this
                this.$merge(this.treeMeta, {
                  conf: {
                    props: {
                      label: label
                    }
                  }
                })
            },
            initMeta(treeObjectCode, tableObjectCode) {
                getTreeMeta(treeObjectCode).then(resp => {
                    this.treeMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                getTableViewMeta(tableObjectCode).then(resp => {
                    let tvMeta = resp.data;
                    this.tvMeta = tvMeta;
                    this.adjustTvMeta()
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            },
            initMetaByFeatureCode(featureCode) {
                getMetaFromFeature_TreeTableTmpl(featureCode).then(resp => {
                    const {tree: treeMeta, table: tvMeta} = resp.data
                    this.treeMeta = treeMeta
                    this.adjustTreeMeta()
                    this.tvMeta = tvMeta
                    this.adjustTvMeta()
                })
            }
        },
        created() {
            const {featureCode} = this;

            loadFeature(featureCode).then(resp => {
                const feature = resp.data;
                this.treeConf = feature['tree'];
                this.tableConf = feature['table'];

                const treeObjectCode = this.treeConf['objectCode'];
                const tableObjectCode = this.tableConf['objectCode'];
                const {foreignFieldCode} = this.tableConf
                if (isEmpty(foreignFieldCode)) { // 表示无法直接寻找外键关联
                    // 通过功能获取meta（在功能层面配置业务拦截器, 兼容关独立联表建立的关联关系）
                    this.initMetaByFeatureCode(featureCode)
                } else { // 存在外键关联
                    this.initMeta(treeObjectCode, tableObjectCode);
                }
            });
        },
        computed: {
            tlRefName() {
                return this.tableConf['objectCode']
            },
            treeRefName() {
                return this.treeConf['objectCode']
            }
        }
    }
</script>

<style scoped>

</style>
