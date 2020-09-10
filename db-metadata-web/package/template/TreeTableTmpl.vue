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
                initTableDataUrl: ''
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
                const {tableConf: {foreignFieldCode}, activeTreeData} = this

                const foreignFieldValue = activeTreeData[foreignFieldCode];
                this.slaves.forEach(slave => {
                  let url = this.$compile(slave['tableUrl'], {foreignFieldValue: foreignFieldValue});
                  slave.tlMeta['data_url'] = url;
                });
            },
            handleActiveChange(row) {
                this.activeTreeData = row
                this.refreshTableData()
            },
            handleAdd() {
              if (utils.isEmpty(this.activeMData)) {
                this.$message.warning('请先选择一条主表记录', '提示');
                return;
              }

              const {$refs, featureCode, activeMData, master} = this;
              const sObjectCode = slave.objectCode;
              const refName = slave.objectCode;
              const foreignKeyName = slave['foreignFieldCode'];
              const foreignKeyValue = activeMData[master['primaryKey']];

              // 主子一对一时, this.$refs[refName]为对象, 主子一对多时, 该结果为数组?
              let ref = utils.isArray($refs[refName]) ? $refs[refName][0] : $refs[refName];

              const url = this.$compile(restUrl.MASTER_SLAVE_TO_ADD_S, {
                objectCode: sObjectCode,
                featureCode: featureCode,
                foreignKeyName: foreignKeyName,
                foreignKeyValue: foreignKeyValue
              });
              ref.dialog(url);
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
                    const {foreignFieldCode} = this.tableConf;
                    const data_url = tvMeta['data_url'] + '?' + foreignFieldCode + '={foreignFieldValue}'; // pxg_todo 关联方式
                    tvMeta['data_url'] = data_url;
                    this.initTableDataUrl = data_url;
                    this.tvMeta = tvMeta;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            },
            initMetaByFeatureCode(featureCode) {
                // 从功能Controller中拿meta
                getMetaFromFeature_TreeTableTmpl(featureCode).then(resp => {
                    const {tree: treeMeta, table: tvMeta} = resp.data
                    this.treeMeta = treeMeta
                    this.tvMeta = tvMeta
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
                this.initMeta(treeObjectCode, tableObjectCode);
                // this.initMetaByFeatureCode(featureCode) // TODO 切换
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
