<template>
    <row-grid :span="[6, 18]">
        <template #left>
            <tree :meta="treeMeta" @active-change="handleActiveChange" @chose-change="handleChoseChange"></tree>
        </template>
        <template #right>
            <form-view :ref="formRefName" :meta="formMeta">
                <template #action="{model, conf}">
                    <slot name="action" v-bind:model="model" v-bind:conf="conf"></slot>
                </template>
            </form-view>
        </template>
    </row-grid>
</template>

<script>
    import utils from '../utils'
    import {restUrl} from '../constant/url'
    import {defaultPrimaryKey} from "../config";
    import {getTreeMeta, loadFeature} from '../utils/rest'
    import DefaultFormViewMeta from '../core/formview/ui-conf'

    export default {
        name: "TreeFormTmpl",
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
                    this.formMeta = this.$merge({}, DefaultFormViewMeta);
                    return;
                }
                const primaryKey = this.primaryKey;
                const primaryValue = utils.extractValue(row, primaryKey);
                const objectCode = this.treeMeta['objectCode'];

                let url = this.$compile(restUrl.RECORD_TO_UPDATE, {
                    objectCode: objectCode,
                    primaryKv: primaryValue
                });
                this.$axios.get(url).then(resp => {
                    this.formMeta = resp.data;
                });
            },
            initMeta(objectCode) {
                getTreeMeta(objectCode).then(resp => {
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
                loadFeature(this.featureCode).then(resp => {
                    const feature = resp.data;
                    this.treeConf = feature['tree'];
                    this.formConf = feature['form'];

                    const {objectCode: treeObjectCode} = this.treeConf;
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
                let primaryKey = this.treeMeta.hasOwnProperty('objectPrimaryKey') ? this.treeMeta['objectPrimaryKey'] : defaultPrimaryKey;
                return primaryKey.split(',');
            }
        }

    }
</script>

<style scoped>

</style>
