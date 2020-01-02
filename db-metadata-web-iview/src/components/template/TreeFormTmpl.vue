<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <tree :meta="treeMeta" @active-change="handleActiveChange" @chose-change="handleChoseChange"></tree>
        </template>
        <template #right>
            <form-tmpl :ref="formRefName" :meta="formMeta"></form-tmpl>
        </template>
    </row-grid>
</template>

<script>
    import utils from '@/utils'
    import {DEFAULT, URL, CONSTANT} from '@/constant'
    import {getFormMeta, getTreeMeta, loadFeature} from "@/components/core/mixins/methods"

    export default {
        name: "TreeFormTmpl",
        mixins: [getFormMeta, getTreeMeta, loadFeature],
        props: {
            fc: String,
            oc: String
        },
        data() {
            const {featureCode: R_fc, objectCode: R_oc} = this.$route.query;
            const featureCode = utils.assertUndefined(this.fc, R_fc);
            const objectCode = utils.assertUndefined(this.oc, R_oc);

            return {
                treeConf: {},
                formConf: {},
                treeMeta: {},
                formMeta: {},
                featureCode: featureCode,
                objectCode: objectCode
            }
        },
        methods: {
            handleChoseChange(rows) {
                // pxg_todo 树多选的默认行为
            },
            handleActiveChange(row) {
                if (utils.isEmpty(row)) {
                    this.formMeta = this.$merge({}, DEFAULT.FormView);
                    return;
                }
                const primaryKey = this.primaryKey;
                const primaryValue = utils.extractValue(row, primaryKey);
                const primaryKv = utils.spliceKvs(primaryKey, primaryValue);
                const objectCode = this.treeMeta['objectCode'];

                let url = this.$compile(URL.RECORD_TO_UPDATE, {
                    objectCode: objectCode,
                    primaryKv: primaryKv
                });
                this.$axios.get(url).then(resp => {
                    this.formMeta = resp.data;
                });
            },
            initMeta(objectCode) {
                this.getTreeMeta(objectCode).then(resp => {
                    this.treeMeta = resp.data;
                }).catch(err => {
                    console.error('[ERROR] msg: %s', err.msg);
                    this.$message.error(err.msg);
                });
            }
        },
        created() {
            const {featureCode, objectCode} = this;
            if (!utils.isEmpty(featureCode)) {
                this.loadFeature(this.featureCode).then(resp => {
                    const feature = resp.data;
                    this.treeConf = feature['tree'];
                    this.formConf = feature['form'];

                    const {treeObjectCode} = this.treeConf;
                    this.initMeta(treeObjectCode);
                });
            } else {
                this.initMeta(objectCode);
            }
        },
        computed: {
            formRefName() {
                return this.formMeta['name'];
            },
            primaryKey() {
                let primaryKey = this.tlMeta.hasOwnProperty('objectPrimaryKey') ? this.tlMeta['objectPrimaryKey'] : CONSTANT.DEFAULT_PRIMARY_KEY;
                return primaryKey.split(',');
            }
        }

    }
</script>

<style scoped>

</style>