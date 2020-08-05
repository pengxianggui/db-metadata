<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <tree :meta="treeMeta" @active-change="handleActiveChange" @chose-change="handleChoseChange"
            ></tree>
        </template>
        <template #right>
            <table-view :ref="tlRefName" :meta="tlMeta">
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
        </template>
    </row-grid>
</template>

<script>
    import utils from '../utils'
    import {getSpMeta, getTlMeta, getTreeMeta, loadFeature} from "../core/mixins/methods"

    export default {
        name: "TreeTableTmpl",
        mixins: [getTlMeta, getSpMeta, getTreeMeta, loadFeature],
        props: {
            fc: String
        },
        data() {
            const {featureCode: R_fc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);

            return {
                featureCode: featureCode,
                treeConf: {},
                tableConf: {},
                tlMeta: {},
                spMeta: {},
                treeMeta: {}
            }
        },
        methods: {
            refresh() {
                const {$refs, tlRefName} = this;
                $refs[tlRefName].getData();
            },
            handleChoseChange(rows) {
                // pxg_todo 多选树节点时, table的默认行为(待定)
            },
            handleActiveChange(row) {
                const {primaryKey} = this.treeConf;

                this.tlMeta['data_url'] = this.$compile(this.tableUrl, {
                    objectCode: row[primaryKey]
                });
            },
            initMeta(treeObjectCode, tableObjectCode) {
                this.getTreeMeta(treeObjectCode).then(resp => {
                    this.treeMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getSpMeta(tableObjectCode).then(resp => {
                    this.spMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });

                this.getTlMeta(tableObjectCode).then(resp => {
                    let tlMeta = resp.data;
                    const {foreignFieldCode} = this.tableConf;
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
            const {featureCode} = this;

            this.loadFeature(featureCode).then(resp => {
                const feature = resp.data;
                this.treeConf = feature['tree'];
                this.tableConf = feature['table'];

                const treeObjectCode = this.treeConf['objectCode'];
                const tableObjectCode = this.tableConf['objectCode'];
                this.initMeta(treeObjectCode, tableObjectCode);
            });
        },
        computed: {
            tlRefName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>
