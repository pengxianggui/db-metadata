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
                <template #add-btn="{conf}">
                  <slot name="add-btn" v-bind:conf="conf" v-bind:featureConf="featureConfig">
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
    import {getMetaFromFeature_TreeTableTmpl, loadFeature} from "../utils/rest";
    import {restUrl} from "../constant/url";
    import {assertEmpty, isEmpty} from "../utils/common";
    import {resolvePath} from "../utils/url";
    import {relatedId, defaultPrimaryKey} from '../config'

    export default {
        name: "TreeTableTmpl",
        props: {
            fc: String
        },
        data() {
            const {fc: R_fc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);

            return {
                featureCode: featureCode,
                featureConfig: {},
                // treeConf: {},
                activeTreeData: {},
                // tableConf: {},
                tvMeta: {},
                treeMeta: {},
                initTableDataUrl: '', // 不带关联参数的Table数据初始url
                tableDataUrl: '' // 待编译的Table数据初始url
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
                const {treePrimaryKey, activeTreeData, tableDataUrl, initTableDataUrl} = this

                let data_url = isEmpty(activeTreeData) ? initTableDataUrl : tableDataUrl
                const foreignFieldValue = activeTreeData[treePrimaryKey];
                let url = this.$compile(data_url, {foreignFieldValue: foreignFieldValue});
                this.tvMeta['data_url'] = url;
            },
            handleActiveChange(row) {
                this.activeTreeData = row
                this.refreshTableData()
            },
            handleAdd() {
              const {$refs, tlRefName, featureCode, activeTreeData, treePrimaryKey} = this

              if (utils.isEmpty(activeTreeData)) {
                this.$message.warning('请先选择树节点', '提示');
                return;
              }

              let {foreignKeyName} = this // Tree、Table关联字段名
              let pathParams = {
                  featureCode: featureCode,
                  foreignKeyName: foreignKeyName,
                  foreignKeyValue: activeTreeData[treePrimaryKey]
              }
              const url = this.$compile(restUrl.TREE_TABLE_TO_ADD_S, pathParams);
              $refs[tlRefName].dialog(url);
            },
            adjustTvMeta() {
                const {tvMeta: {data_url: initDataUrl}, foreignKeyName} = this
                const pathParams = {}
                pathParams[foreignKeyName] = "{foreignFieldValue}"
                this.initTableDataUrl = initDataUrl
                const data_url = resolvePath(initDataUrl, pathParams)
                this.tableDataUrl = data_url
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
            // initMeta(treeObjectCode, tableObjectCode) {
            //     getTreeMeta(treeObjectCode).then(resp => {
            //         this.treeMeta = resp.data;
            //     }).catch(err => {
            //         console.error('[ERROR] msg: %s', err.msg);
            //         this.$message.error(err.msg);
            //     });
            //
            //     getTableViewMeta(tableObjectCode).then(resp => {
            //         let tvMeta = resp.data;
            //         this.tvMeta = tvMeta;
            //         this.adjustTvMeta()
            //     }).catch(err => {
            //         console.error('[ERROR] msg: %s', err.msg);
            //         this.$message.error(err.msg);
            //     });
            // },
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
                this.featureConfig = resp.data;
                this.initMetaByFeatureCode(featureCode)
            });
        },
        computed: {
            tlRefName() {
                const {tableConf: {objectCode: tlRefName} = {}} = this
                return tlRefName
            },
            treeRefName() {
                const {treeConf: {objectCode: treeRefName} = {}} = this
                return treeRefName
            },
            foreignKeyName() { // Table 关联树的外键字段, 若为中间表关联, tableConf.foreignFieldCode为空, 则取_related_id为关联key, 由Feature AOP去寻找关联关系
                const {tableConf: {foreignFieldCode} = {}} = this
                return assertEmpty(foreignFieldCode, relatedId)
            },
            treePrimaryKey() { // Tree的主键： 它为构建树时的主键，也为Table关联的主键
                const {treeConf: {idKey} = {}} = this
                return assertEmpty(idKey, defaultPrimaryKey)
            },
            treeConf() {
                return this.featureConfig['tree']
            },
            tableConf() {
                return this.featureConfig['table']
            }
        }
    }
</script>

<style scoped>

</style>
