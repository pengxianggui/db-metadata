<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <tree :meta="treeMeta" @active-change="handleActiveChange"></tree>
        </template>
        <template #right>
            <table-list :ref="tlRefName" :meta="tlMeta"></table-list>
        </template>
    </row-grid>
</template>

<script>
    import utils from '@/utils'
    import {getTlMeta, getSpMeta, getTreeMeta, loadFeature} from "@/components/core/mixins/methods"

    export default {
        name: "TreeTableTmpl",
        mixins: [getTlMeta, getSpMeta, getTreeMeta, loadFeature],
        props: ["fc"],
        data() {
            const featureCode = utils.assertUndefined(this.fc, this.$route.query.featureCode);
            return {
                featureCode: featureCode,
                treeConf: {},
                tableConf: {},
                tlMeta: {},
                spMeta: {},
                treeMeta: {
                    component_name: 'Tree',
                    name: 'Tree', // 可省略, value不指定时, 则选取的值即为整个item对象
                    label: 'Tree',
                    conf: {
                        // other options
                        props: {
                            label: "name",
                            children: "child"
                        }
                    },
                }
            }
        },
        methods: {
            handleActiveChange(row) {
                const {primaryKey} = this.treeConf;

                this.tlMeta['data_url'] = this.$compile(this.tableUrl, {
                    objectCode: row[primaryKey]
                });
            },

        },
        created() {
            this.loadFeature(this.featureCode).then(resp => {
                const feature = resp.data;
                this.treeConf = feature['tree'];
                this.tableConf = feature['table'];

                const treeObjectCode = this.treeConf['objectCode'];
                const tableObjectCode = this.tableConf['objectCode'];
                const foreignFieldCode = this.tableConf['foreignFieldCode'];

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
            treeRefName() {
                return this.treeMeta['name'];
            },
            tlRefName() {
                return this.tlMeta['name'];
            }
        }
    }
</script>

<style scoped>

</style>